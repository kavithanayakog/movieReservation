package com.example.movie_reservation.service;

import com.example.movie_reservation.model.Seat;
import com.example.movie_reservation.requestDTO.SeatRequestDTO;
import com.example.movie_reservation.responseDTO.SeatResponseDTO;

import java.util.List;

public interface SeatService {

    SeatResponseDTO createSeat(SeatRequestDTO seat);

    SeatResponseDTO getSeatById(Long seatId);

    List<SeatResponseDTO> getAllSeats();

    List<SeatResponseDTO> getSeatsByScreen(Long screenId);

    SeatResponseDTO updateSeat(Long seatId, SeatRequestDTO seat);

    void deleteSeat(Long seatId);
}

