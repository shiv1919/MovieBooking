package com.example.MovieBooking.service;

import com.example.MovieBooking.Dto.RequestDto.*;
import com.example.MovieBooking.Dto.ResponseDto.HistoryResponseDTO;
import com.example.MovieBooking.Dto.ResponseDto.MovieResponseDTO;
import com.example.MovieBooking.Mapper.HistoryMapper;
import com.example.MovieBooking.Mapper.MovieMapper;
import com.example.MovieBooking.repository.AdminRepository;
import com.example.MovieBooking.repository.HistoryRepository;
import com.example.MovieBooking.repository.MovieRepository;
import com.example.MovieBooking.repository.SeatbookingRepository;
import com.example.MovieBooking.table.Admin;
import com.example.MovieBooking.table.History;
import com.example.MovieBooking.table.Movie;
import com.example.MovieBooking.table.Seatbooking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private HistoryRepository historyRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private MovieMapper movieMapper;

    @Autowired
    private HistoryMapper historyMapper;

    @Value("${spring.mail.username}")
    private String sender;

    @Autowired
    private SeatbookingRepository seatbookingRepository;

    public Optional<MovieResponseDTO> getMovieById(Integer id) {
        return movieRepository.findById(id)
                .map(movieMapper::movieToMovieResponseDTO);

    }

    public List<MovieResponseDTO> getAllMovies() {
        List<Movie> movies = movieRepository.findAll();
        return movies.stream()
                .map(movieMapper::movieToMovieResponseDTO)
                .collect(Collectors.toList());
    }

    public Iterable<HistoryResponseDTO> getHistory() {
        List<History> history = historyRepository.findAll();
        return history.stream()
                .map(historyMapper::historyToHistoryResponseDTO)
                .collect(Collectors.toList());
    }

    public Admin addAdmin(AdminRequestDTO adminRequestDTO) {
        String username=adminRequestDTO.getUsername();
        String password=adminRequestDTO.getPassword();
        Admin admin = new Admin();
        admin.setUsername(username);
        admin.setPassword(password);
        adminRepository.save(admin);
        return admin;
    }

    public boolean addMovie(MovieRequestDTO movieRequestDTO) {
        String username=movieRequestDTO.getUsername();
        String password=movieRequestDTO.getPassword();
        if (verifyAdmin(username, password)) {
            Movie movie = movieMapper.movieRequestDTOToMovie(movieRequestDTO);
            movieRepository.save(movie);
            int totalSeats=movieRequestDTO.getTotalseats();
            for (int i = 1; i <= totalSeats; i++) {
                Seatbooking seat = new Seatbooking();
                seat.setMovieId(movie.getId());
                seat.setSeatNumber(i);
                seat.setBooked(false);
                seatbookingRepository.save(seat);
            }

            return true;
        }
        return false;
    }

    public boolean deleteMovie(DeleteRequestDTO deleteRequestDTO) {
        int id=deleteRequestDTO.getId();
        String username=deleteRequestDTO.getUsername();
        String password=deleteRequestDTO.getPassword();
        Optional<Movie> movie = movieRepository.findById(id);
        if (movie.isPresent() && verifyAdmin(username, password)) {
            movieRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean bookTicket(BookTicketRequestDTO bookTicketRequestDTO ) {
        int id=bookTicketRequestDTO.getId();
        long contact=bookTicketRequestDTO.getContact();
        String mail=bookTicketRequestDTO.getMail();
        Integer[] seatNumbers=bookTicketRequestDTO.getSeatNumbers();
        LocalDate date=bookTicketRequestDTO.getDate();
        Optional<Movie> movie = movieRepository.findById(id);
        if (!movie.isPresent()) {
            throw new IllegalArgumentException("Movie not found.");
        }

        int availableSeats = movie.get().getAvailableseats();
        if (seatNumbers.length > availableSeats) {
            throw new IllegalArgumentException(availableSeats + " seats available at this time.");
        }

        for (Integer seatNumber : seatNumbers) {
            Seatbooking seat = seatbookingRepository.findByMovieIdAndSeatNumber(id, seatNumber);
            if (seat == null || seat.isBooked()) {
                throw new IllegalArgumentException("Seat number " + seatNumber + " is already booked or does not exist.");
            }
        }

        movieRepository.deleteseat(id, seatNumbers.length);

        for (Integer seatNumber : seatNumbers) {
            Seatbooking seat = seatbookingRepository.findByMovieIdAndSeatNumber(id, seatNumber);
            seat.setBooked(true);
            seat.setBookedBy(mail);
            seat.setBookingDate(LocalDateTime.now());
            seatbookingRepository.save(seat);
        }

        History history = new History();
        history.setMovieid(id);
        history.setBookedseats(seatNumbers.length);
        history.setBookingdate(date);
        history.setContact(contact);
        historyRepository.save(history);

        sendMail(mail, seatNumbers.length, date, contact);

        return true;
    }

    public String sendMail(String mail, Integer seats, LocalDate date, long contact) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(sender);
            mailMessage.setTo(mail);
            mailMessage.setSubject("Booking Confirmation");
            mailMessage.setText("Seats: " + seats + "\nDate: " + date + "\nContact: " + contact);
            javaMailSender.send(mailMessage);
            return "Mail Sent Successfully.";
        } catch (Exception e) {
            return "Error while Sending Mail.";
        }
    }

    public boolean verifyAdmin(String username, String password) {
        return adminRepository.verify(username, password);
    }

    public List<Seatbooking> getAllSeatBookingsByMovieId(int movieId) {
        return seatbookingRepository.findByMovieId(movieId);
    }

    public List<Seatbooking> getAvailableSeats(int movieId) {
        return seatbookingRepository.findByMovieIdAndIsBooked(movieId, false);
    }

    public Seatbooking cancelSeatBooking(CancelTicketRequestDTO cancelTicketRequestDTO) {
        int movieId=cancelTicketRequestDTO.getMovieId();
        int seatNumber= cancelTicketRequestDTO.getSeatNumber();
        Seatbooking seat = seatbookingRepository.findByMovieIdAndSeatNumber(movieId, seatNumber);

        if (seat != null && seat.isBooked()) {
            seat.setBooked(false);
            seat.setBookedBy(null);
            seat.setBookingDate(null);
            return seatbookingRepository.save(seat);
        }
        return null;
    }
}
