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
public class HistoryResponseDTO {
    private Long contact;
    private Integer movieId;
    private Integer bookedSeats;
    private LocalDate bookingDate;
}
