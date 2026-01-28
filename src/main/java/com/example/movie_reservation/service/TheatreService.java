package com.example.movie_reservation.service;

import com.example.movie_reservation.model.Theatre;

import java.util.List;

public interface TheatreService {

    Theatre createTheatre(Theatre theatre);

    Theatre getTheatreById(Long theatreId);

    List<Theatre> getAllTheatres();

    Theatre updateTheatre(Long theatreId, Theatre theatre);

    void deleteTheatre(Long theatreId);

}
