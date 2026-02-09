package com.example.movie_reservation.service;

import com.example.movie_reservation.model.Theatre;
import com.example.movie_reservation.requestDTO.TheatreRequestDTO;
import com.example.movie_reservation.responseDTO.TheatreResponseDTO;

import java.util.List;

public interface TheatreService {

    TheatreResponseDTO createTheatre(TheatreRequestDTO theatre);

    Theatre getTheatreById(Long theatreId);

    List<Theatre> getAllTheatres();

    TheatreResponseDTO updateTheatre(Long theatreId, TheatreRequestDTO theatre);

    void deleteTheatre(Long theatreId);

}
