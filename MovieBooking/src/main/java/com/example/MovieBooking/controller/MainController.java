package com.example.MovieBooking.controller;

import com.example.MovieBooking.Dto.RequestDto.*;
import com.example.MovieBooking.Dto.ResponseDto.HistoryResponseDTO;
import com.example.MovieBooking.Dto.ResponseDto.MovieResponseDTO;
import jakarta.validation.Valid;
import com.example.MovieBooking.service.UserService;
import com.example.MovieBooking.table.Admin;
import com.example.MovieBooking.table.Seatbooking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import java.time.LocalDate;
import java.util.Optional;

@RestController
public class MainController {
    @Autowired
    private UserService userService;

    @GetMapping("/getMovie/{id}")
    public ResponseEntity<?> getMovie(@PathVariable int id){
        Optional<MovieResponseDTO> movie= userService.getMovieById(id);
        if (movie.isPresent()) {
            return ResponseEntity.ok(movie.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<Iterable<MovieResponseDTO>> getAllMovies(){
        return ResponseEntity.ok(userService.getAllMovies());
    }

    @GetMapping("/gethistory")
    public ResponseEntity<Iterable<HistoryResponseDTO>> getistory(){
        return ResponseEntity.ok(userService.getHistory());
    }

    @PostMapping("/addadmin")
    public ResponseEntity<Admin> addadmin(@Valid @RequestBody AdminRequestDTO adminRequestDTO){
        return ResponseEntity.ok(userService.addAdmin(adminRequestDTO));
    }

    @PostMapping("/addmovie")
    public ResponseEntity<Boolean> addmovie(@Valid @RequestBody MovieRequestDTO movieRequestDTO){
        boolean isMovie=userService.addMovie(movieRequestDTO);
        return ResponseEntity.ok(isMovie);
    }

    @DeleteMapping("/deletemovie")
    public ResponseEntity<Boolean> deletemovie(@Valid @RequestBody DeleteRequestDTO deleteRequestDTO){
        boolean isDelete=userService.deleteMovie(deleteRequestDTO);
        return ResponseEntity.ok(isDelete);
    }

    @PostMapping("/bookticket")
    public ResponseEntity<?> bookTicket(@Valid @RequestBody BookTicketRequestDTO bookTicketRequestDTO) {
        try {
            boolean result = userService.bookTicket(bookTicketRequestDTO);
            return ResponseEntity.ok("Ticket booking successful!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An unexpected error occurred.");
        }
    }

    @GetMapping("/seatbookings/{movieId}")
    public ResponseEntity<List<Seatbooking>> getAllSeatBookingsByMovieId(@PathVariable int movieId) {
        List<Seatbooking> seatBookings = userService.getAllSeatBookingsByMovieId(movieId);
        if (seatBookings.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(seatBookings);
    }

    @GetMapping("/available-seats/{movieId}")
    public ResponseEntity<List<Seatbooking>> getAvailableSeats(@PathVariable int movieId) {
        List<Seatbooking> availableSeats = userService.getAvailableSeats(movieId);
        if (availableSeats.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(availableSeats);
    }

    @PostMapping("/cancel-seat-booking")
    public ResponseEntity<String> cancelSeatBooking(@Valid @RequestBody CancelTicketRequestDTO cancelTicketRequestDTO) {
        Seatbooking canceledSeat = userService.cancelSeatBooking(cancelTicketRequestDTO);
        if (canceledSeat != null) {
            return ResponseEntity.ok("Seat booking canceled successfully.");
        } else {
            return ResponseEntity.badRequest().body("Seat not found or not booked.");
        }
    }
}
