package com.example.MovieBooking.service;

import com.example.MovieBooking.repository.AdminRepository;
import com.example.MovieBooking.repository.HistoryRepository;
import com.example.MovieBooking.repository.MovieRepository;
import com.example.MovieBooking.table.Admin;
import com.example.MovieBooking.table.History;
import com.example.MovieBooking.table.Movie;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

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
    @Value("${spring.mail.username}") private String sender;

    public Optional<Movie> getMovieById(Integer id) {
        return movieRepository.findById(id);
    }
    public Iterable<Movie> getAllMovie(){
        return movieRepository.findAll();
    }

    public Iterable<History> gethistory(){
        return historyRepository.findAll();
    }

    public Admin addadmin(String username,String password){
        Admin admin=new Admin();
        admin.setUsername(username);
        admin.setPassword(password);
        adminRepository.save(admin);
        return admin;
    }
    public boolean addmovie(String username,String password,String title, String director, String description, String genre, LocalDate date, String location, Integer totalSeats, Integer availableSeats, Integer price){
        if(verifyadmin(username, password)){
            Movie movie=new Movie();
            movie.setTitle(title);
            movie.setDirector(director);
            movie.setDescription(description);
            movie.setGenre(genre);
            movie.setDate(date);
            movie.setLocation(location);
            movie.setTotalseats(totalSeats);
            movie.setAvailableseats(availableSeats);
            movie.setPrice(price);
            movieRepository.save(movie);
            return true;
        }
        return false;
    }
    public boolean deletemovie(String username,String password,Integer id) {
        Optional<Movie> movie = movieRepository.findById(id);
        if (movie.isPresent() && verifyadmin(username, password)) {
            movieRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public boolean  bookticket(Integer id,long contact,String mail,Integer seats,LocalDate date){
        Optional<Movie> movie=movieRepository.findById(id);
        if (!movie.isPresent()) {
            throw new IllegalArgumentException("Movie not found.");
        }
        int availableseats= movie.get().getAvailableseats();
        if(seats>availableseats) throw new IllegalArgumentException(availableseats+" seats available at this time.");
        movieRepository.deleteseat(id,seats);

        History history=new History();
        history.setMovieid(id);
        history.setBookedseats(seats);
        history.setBookingdate(date);
        history.setContact(contact);
        historyRepository.save(history);
        sendmail(mail,seats,date,contact);
        return true;
    }

    public String sendmail(String mail,Integer seats,LocalDate date,long contact){
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            String subject="Booking Conformation";
            mailMessage.setFrom(sender);
            mailMessage.setTo(mail);
            mailMessage.setText("seats :"+seats+"\nDate : "+date+"\nContact :"+contact);
            mailMessage.setSubject(subject);

            javaMailSender.send(mailMessage);
            return "Mail Sent Successfully...";
        }
        catch (Exception e) {
            return "Error while Sending Mail";
        }
    }

    public boolean verifyadmin(String username,String password){
        return adminRepository.verify(username, password);
    }

}
