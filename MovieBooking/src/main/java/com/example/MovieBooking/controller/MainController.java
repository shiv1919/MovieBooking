package com.example.MovieBooking.controller;

import com.example.MovieBooking.service.UserService;
import com.example.MovieBooking.table.Admin;
import com.example.MovieBooking.table.History;
import com.example.MovieBooking.table.Movie;
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

    @GetMapping("/getMovie")
    public ResponseEntity<?> getMovie(@RequestParam Integer id){
        Optional<Movie> movie= userService.getMovieById(id);
        if (movie.isPresent()) {
            return ResponseEntity.ok(movie.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<Iterable<Movie>> getAllMovies(){
        return ResponseEntity.ok(userService.getAllMovies());
    }

    @GetMapping("/gethistory")
    public ResponseEntity<Iterable<History>> getistory(){
        return ResponseEntity.ok(userService.getHistory());
    }

    @PostMapping("/addadmin")
    public ResponseEntity<Admin> addadmin(@RequestParam String username,@RequestParam String password){
        return ResponseEntity.ok(userService.addAdmin(username,password));
    }

    @PostMapping("/addmovie")
    public ResponseEntity<Boolean> addmovie(@RequestParam String username, @RequestParam String password,@RequestParam String title, @RequestParam String director, @RequestParam String description, @RequestParam String genre, @RequestParam LocalDate date, @RequestParam String location, @RequestParam Integer totalSeats, @RequestParam Integer availableSeats, @RequestParam Integer price){
        boolean isMovie=userService.addMovie(username,password,title, director, description,genre,date,location,totalSeats,availableSeats,price);
        return ResponseEntity.ok(isMovie);
    }

    @DeleteMapping("/deletemovie")
    public ResponseEntity<Boolean> deletemovie(@RequestParam String username, @RequestParam String password,@RequestParam Integer id){
        boolean isDelete=userService.deleteMovie(username,password,id);
        return ResponseEntity.ok(isDelete);
    }

    @PostMapping("/bookticket")
    public ResponseEntity<?> bookTicket(
            @RequestParam Integer id,
            @RequestParam long contact,
            @RequestParam String mail,
            @RequestParam Integer[] seatNumbers,
            @RequestParam LocalDate date) {
        try {
            boolean result = userService.bookTicket(id, contact, mail, seatNumbers, date);
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
    public ResponseEntity<String> cancelSeatBooking(
            @RequestParam int movieId,
            @RequestParam int seatNumber) {
        Seatbooking canceledSeat = userService.cancelSeatBooking(movieId, seatNumber);
        if (canceledSeat != null) {
            return ResponseEntity.ok("Seat booking canceled successfully.");
        } else {
            return ResponseEntity.badRequest().body("Seat not found or not booked.");
        }
    }
}
