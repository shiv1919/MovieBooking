package com.example.MovieBooking.repository;

import com.example.MovieBooking.table.Movie;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface MovieRepository extends JpaRepository<Movie,Integer> {
    @Modifying
    @Transactional
    @Query("DELETE FROM Movie m WHERE m.id = :id")
    void deleteMovieById(Integer id);
    @Modifying
    @Transactional
    @Query("update Movie m set m.availableseats=m.availableseats-:seats where m.id=:id")
    void deleteseat(Integer id,Integer seats);
}

