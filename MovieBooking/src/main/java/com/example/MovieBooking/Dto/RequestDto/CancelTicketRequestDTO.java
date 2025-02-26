package com.example.MovieBooking.Dto.RequestDto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CancelTicketRequestDTO {
    @NotNull(message = "Movie ID cannot be null")
    @Min(value = 1, message = "Movie ID must be greater than 0")
    private Integer movieId;

    @NotNull(message = "Seat number cannot be null")
    @Min(value = 1, message = "Seat number must be greater than 0")
    private Integer seatNumber;
}
