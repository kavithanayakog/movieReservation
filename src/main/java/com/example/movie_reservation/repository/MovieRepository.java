package com.example.movie_reservation.repository;

import com.example.movie_reservation.model.Movie;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie , Long> {

    Optional<Movie> findByNameIgnoreCase(String movieName);

    boolean existsByNameIgnoreCaseAndMovieIdNot(@NotBlank String name, Long movieId);
}
