package com.example.MovieBooking.repository;

import com.example.MovieBooking.table.Seatbooking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatbookingRepository extends JpaRepository<Seatbooking, Integer> {

    List<Seatbooking> findByMovieId(int movieId);

    List<Seatbooking> findByMovieIdAndIsBooked(int movieId, boolean isBooked);

    Seatbooking findByMovieIdAndSeatNumber(int movieId, int seatNumber);
}
