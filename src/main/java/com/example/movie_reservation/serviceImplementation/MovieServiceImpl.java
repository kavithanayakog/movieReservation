package com.example.movie_reservation.serviceImplementation;

import com.example.movie_reservation.model.Movie;
import com.example.movie_reservation.model.Role;
import com.example.movie_reservation.repository.MovieRepository;
import com.example.movie_reservation.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    @Override
    public Movie createMovie(Movie movie) {
        movieRepository.findByNameIgnoreCase
                        (movie.getName())
                .ifPresent(r -> {
                    throw new RuntimeException("Movie already exists");
                });
        return movieRepository.save(movie);
    }

    @Override
    public Movie getMovieById(Long movieId) {
        return movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Movie not found"));
    }

    @Override
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    @Override
    public Movie updateMovie(Long movieId, Movie movie) {
        Movie existingMovie = getMovieById(movieId);

        existingMovie.setName(movie.getName());
        existingMovie.setLanguage(movie.getLanguage());
        existingMovie.setDuration(movie.getDuration());
        return movieRepository.save(existingMovie);
    }

    @Override
    public void deleteMovie(Long movieId) {
        Movie movie = getMovieById(movieId);
        movieRepository.delete(movie);
    }
}
