package com.example.MovieBooking.controller;

import com.example.MovieBooking.service.UserService;
import com.example.MovieBooking.table.Admin;
import com.example.MovieBooking.table.History;
import com.example.MovieBooking.table.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            return ResponseEntity.ok(movie.get());  // Return the movie if found
        } else {
            return ResponseEntity.notFound().build();  // Return 404 if movie is not found
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<Iterable<Movie>> getAllMovies(){
        return ResponseEntity.ok(userService.getAllMovie());
    }

    @GetMapping("/gethistory")
    public ResponseEntity<Iterable<History>> gethistory(){
        return ResponseEntity.ok(userService.gethistory());
    }

    @PostMapping("/addadmin")
    public ResponseEntity<Admin> addadmin(@RequestParam String username,@RequestParam String password){
        return ResponseEntity.ok(userService.addadmin(username,password));
    }

    @PostMapping("/addmovie")
    public ResponseEntity<Boolean> addmovie(@RequestParam String username, @RequestParam String password,@RequestParam String title, @RequestParam String director, @RequestParam String description, @RequestParam String genre, @RequestParam LocalDate date, @RequestParam String location, @RequestParam Integer totalSeats, @RequestParam Integer availableSeats, @RequestParam Integer price){
        boolean isMovie=userService.addmovie(username,password,title, director, description,genre,date,location,totalSeats,availableSeats,price);
        return ResponseEntity.ok(isMovie);
    }

    @DeleteMapping("/deletemovie")
    public ResponseEntity<Boolean> deletemovie(@RequestParam String username, @RequestParam String password,@RequestParam Integer id){
        boolean isDelete=userService.deletemovie(username,password,id);
        return ResponseEntity.ok(isDelete);
    }

    @PostMapping("/bookticket")
    public ResponseEntity<?> bookticket(@RequestParam Integer id,@RequestParam long contact,@RequestParam String mail,@RequestParam  Integer seats,@RequestParam LocalDate date){
        return ResponseEntity.ok(userService.bookticket(id,contact,mail,seats,date));

    }



}
