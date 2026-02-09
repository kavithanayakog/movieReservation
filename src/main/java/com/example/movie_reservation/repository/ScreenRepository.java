package com.example.movie_reservation.repository;

import com.example.movie_reservation.model.Screen;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScreenRepository extends JpaRepository<Screen,Long> {

    boolean existsByScreenNameAndTheatre_TheatreId(String screenName, Long theatreId);

    List<Screen> findByTheatre_TheatreId(Long theatreId);
}
