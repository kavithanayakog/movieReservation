package com.example.movie_reservation.repository;

import com.example.movie_reservation.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie , Long> {

    Optional<Movie> findByNameIgnoreCase(String movieName);
}
