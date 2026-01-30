package com.example.movie_reservation.service;

import com.example.movie_reservation.model.ShowSeat;

import java.util.List;

public interface ShowSeatService {

    ShowSeat createShowSeat(Long showId, Long seatId);

    ShowSeat getShowSeatById(Long showSeatId);

    List<ShowSeat> getShowSeatsByShow(Long showId);

    ShowSeat updateAvailability(Long showSeatId, Boolean isAvailable);

    void deleteShowSeat(Long showSeatId);
}
