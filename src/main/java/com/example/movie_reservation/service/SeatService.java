package com.example.movie_reservation.service;

import com.example.movie_reservation.model.Seat;
import com.example.movie_reservation.requestDTO.SeatRequestDTO;
import com.example.movie_reservation.responseDTO.SeatResponseDTO;

import java.util.List;

public interface SeatService {

    SeatResponseDTO createSeat(SeatRequestDTO seat);

    Seat getSeatById(Long seatId);

    List<Seat> getAllSeats();

    List<Seat> getSeatsByScreen(Long screenId);

    SeatResponseDTO updateSeat(Long seatId, SeatRequestDTO seat);

    void deleteSeat(Long seatId);
}

