package com.example.movie_reservation.repository;

import com.example.movie_reservation.model.Movie;
import com.example.movie_reservation.model.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TheatreRepository extends JpaRepository<Theatre,Long> {

    List<Theatre> findByCityIgnoreCase(String city);
    Optional<Theatre> findByNameIgnoreCase(String theatreName);
}
