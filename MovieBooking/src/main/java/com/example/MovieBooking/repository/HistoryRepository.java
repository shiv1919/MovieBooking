package com.example.MovieBooking.repository;

import com.example.MovieBooking.table.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryRepository extends JpaRepository<History,Integer> {
}
