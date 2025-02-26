package com.example.MovieBooking.Mapper;

import com.example.MovieBooking.Dto.ResponseDto.HistoryResponseDTO;
import com.example.MovieBooking.table.History;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HistoryMapper {
    HistoryResponseDTO historyToHistoryResponseDTO(History history);
}
