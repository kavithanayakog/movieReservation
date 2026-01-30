package com.example.movie_reservation.service;

import com.example.movie_reservation.model.Screen;

import java.util.List;

public interface ScreenService {

    Screen createScreen(Screen screen);

    Screen getScreenById(Long screenId);

    List<Screen> getAllScreens();

    List<Screen> getScreensByTheatre(Long theatreId);

    Screen updateScreen(Long screenId, Screen screen);

    void deleteScreen(Long screenId);

}
