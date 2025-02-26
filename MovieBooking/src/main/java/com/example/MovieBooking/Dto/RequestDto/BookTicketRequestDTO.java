package com.example.MovieBooking.Dto.RequestDto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookTicketRequestDTO {
    @NotNull(message = "Movie ID cannot be null")
    private Integer id;

    @Min(value = 1000000000, message = "Contact number must be at least 10 digits")
    private long contact;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email cannot be blank")
    private String mail;

    @NotEmpty(message = "Seat numbers cannot be empty")
    private Integer[] seatNumbers;

    @NotNull(message = "Booking date cannot be null")
    private LocalDate date;
}
