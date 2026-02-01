package com.example.movie_reservation.service;

import com.example.movie_reservation.model.SeatType;

import java.util.List;

public interface SeatTypeService {

    SeatType createSeatType(SeatType seatType);
    SeatType getSeatTypeById(Long id);
    SeatType updateSeatType(Long id, SeatType seatType);
    void deleteSeatType(Long id);
    List<SeatType> getAllSeatTypes();

}
