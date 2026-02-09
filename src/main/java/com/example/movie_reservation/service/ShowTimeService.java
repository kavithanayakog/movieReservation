package com.example.movie_reservation.service;

import com.example.movie_reservation.model.ShowTime;
import com.example.movie_reservation.requestDTO.ShowTimeRequestDTO;
import com.example.movie_reservation.responseDTO.ShowTimeResponseDTO;

import java.util.List;

public interface ShowTimeService {

    ShowTimeResponseDTO createShowTime(ShowTimeRequestDTO showTime);

    List<ShowTime> getAllShowTime();

    ShowTime getShowTimeById(Long showTimeId);

    ShowTimeResponseDTO updateShowTime(Long showTimeId, ShowTimeRequestDTO showTime);

    void deleteShowTime(Long showTimeId);

    List<ShowTime> getShowsByMovie(Long movieId);
}
