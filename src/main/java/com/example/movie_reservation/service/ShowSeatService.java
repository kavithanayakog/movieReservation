package com.example.movie_reservation.service;

import com.example.movie_reservation.model.ShowSeat;
import com.example.movie_reservation.responseDTO.ShowSeatResponseDTO;

import java.util.List;

public interface ShowSeatService {

    ShowSeatResponseDTO createShowSeat(Long showId, Long seatId);

    ShowSeatResponseDTO getShowSeatById(Long showSeatId);

    List<ShowSeatResponseDTO> getShowSeatsByShow(Long showId);

    ShowSeatResponseDTO updateAvailability(Long showSeatId, Boolean isAvailable);

    void deleteShowSeat(Long showSeatId);
}
