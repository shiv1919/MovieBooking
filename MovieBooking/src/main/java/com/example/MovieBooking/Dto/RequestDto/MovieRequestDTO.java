package com.example.MovieBooking.Dto.RequestDto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieRequestDTO {

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required")
    private String password;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Director is required")
    private String director;

    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "Genre is required")
    private String genre;

    @NotNull(message = "Date is required")
    @Future(message = "Date must be in the future")
    private LocalDate date;

    @NotBlank(message = "Location is required")
    private String location;

    @NotNull(message = "Total seats are required")
    @Min(value = 1, message = "Total seats must be at least 1")
    private Integer totalseats;

    @NotNull(message = "Available seats are required")
    @Min(value = 0, message = "Available seats cannot be negative")
    private Integer availableseats;

    @NotNull(message = "Price is required")
    @Min(value = 0, message = "Price cannot be negative")
    private Integer price;
}
