package com.example.movie_reservation.service;

import com.example.movie_reservation.model.SeatType;
import com.example.movie_reservation.requestDTO.SeatTypeRequestDTO;
import com.example.movie_reservation.responseDTO.SeatTypeResponseDTO;

import java.util.List;

public interface SeatTypeService {

    SeatTypeResponseDTO createSeatType(SeatTypeRequestDTO seatType);
    SeatType getSeatTypeById(Long id);
    SeatTypeResponseDTO updateSeatType(Long id, SeatTypeRequestDTO seatType);
    void deleteSeatType(Long id);
    List<SeatType> getAllSeatTypes();

}
