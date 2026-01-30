package com.example.movie_reservation.service;

import com.example.movie_reservation.model.ShowTime;

import java.util.List;

public interface ShowTimeService {

    ShowTime createShowTime(ShowTime showTime);

    List<ShowTime> getAllShowTime();

    ShowTime getShowTimeById(Long showTimeId);

    ShowTime updateShowTime(Long showTimeId, ShowTime showTime);

    void deleteShowTime(Long showTimeId);

    List<ShowTime> getShowsByMovie(Long movieId);
}
