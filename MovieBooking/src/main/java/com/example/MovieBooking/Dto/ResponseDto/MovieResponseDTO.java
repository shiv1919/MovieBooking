package com.example.MovieBooking.Dto.ResponseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieResponseDTO {
    private Integer id;
    private String title;
    private String director;
    private String description;
    private String genre;
    private LocalDate date;
    private String location;
    private Integer totalSeats;
    private Integer availableSeats;
    private Integer price;
}
