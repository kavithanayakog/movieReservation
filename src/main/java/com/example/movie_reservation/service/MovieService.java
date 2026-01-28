package com.example.movie_reservation.service;

import com.example.movie_reservation.model.Movie;

import java.util.List;

public interface MovieService {

    Movie createMovie(Movie movie);

    Movie getMovieById(Long movieId);

    List<Movie> getAllMovies();

    Movie updateMovie(Long movieId, Movie movie);

    void deleteMovie(Long movieId);
}
