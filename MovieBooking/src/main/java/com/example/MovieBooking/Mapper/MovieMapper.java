package com.example.MovieBooking.Mapper;

import com.example.MovieBooking.Dto.RequestDto.MovieRequestDTO;
import com.example.MovieBooking.Dto.ResponseDto.MovieResponseDTO;
import com.example.MovieBooking.table.Movie;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MovieMapper {
    MovieResponseDTO movieToMovieResponseDTO(Movie movie);
    Movie movieRequestDTOToMovie(MovieRequestDTO movieRequestDTO);
}
