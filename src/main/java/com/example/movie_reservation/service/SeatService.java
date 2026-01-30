package com.example.movie_reservation.service;

import com.example.movie_reservation.model.Seat;

import java.util.List;

public interface SeatService {

    Seat createSeat(Seat seat);

    Seat getSeatById(Long seatId);

    List<Seat> getAllSeats();

    List<Seat> getSeatsByScreen(Long screenId);

    Seat updateSeat(Long seatId, Seat seat);

    void deleteSeat(Long seatId);
}

