package com.example.movie_reservation.service;

import com.example.movie_reservation.model.Movie;
import com.example.movie_reservation.requestDTO.MovieRequestDTO;
import com.example.movie_reservation.responseDTO.MovieResponseDTO;

import java.util.List;

public interface MovieService {

    MovieResponseDTO createMovie(MovieRequestDTO movie);

    Movie getMovieById(Long movieId);

    List<Movie> getAllMovies();

    MovieResponseDTO updateMovie(Long movieId, MovieRequestDTO movie);

    void deleteMovie(Long movieId);
}
