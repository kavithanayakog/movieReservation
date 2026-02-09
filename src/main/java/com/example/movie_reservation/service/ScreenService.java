package com.example.movie_reservation.service;

import com.example.movie_reservation.model.Screen;
import com.example.movie_reservation.requestDTO.ScreenRequestDTO;
import com.example.movie_reservation.responseDTO.ScreenResponseDTO;

import java.util.List;

public interface ScreenService {

    ScreenResponseDTO createScreen(ScreenRequestDTO screen);

    Screen getScreenById(Long screenId);

    List<Screen> getAllScreens();

    List<Screen> getScreensByTheatre(Long theatreId);

    ScreenResponseDTO updateScreen(Long screenId, ScreenRequestDTO screen);

    void deleteScreen(Long screenId);

}
