package com.example.movie_reservation.serviceImplementation;

import com.example.movie_reservation.exception.ResourceNotFoundException;
import com.example.movie_reservation.model.Movie;
import com.example.movie_reservation.model.Role;
import com.example.movie_reservation.model.User;
import com.example.movie_reservation.repository.MovieRepository;
import com.example.movie_reservation.requestDTO.MovieRequestDTO;
import com.example.movie_reservation.responseDTO.MovieResponseDTO;
import com.example.movie_reservation.responseDTO.UserResponseDTO;
import com.example.movie_reservation.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    @Override
    public MovieResponseDTO createMovie(MovieRequestDTO movieRequest) throws ResourceNotFoundException {

        movieRepository.findByNameIgnoreCase
                        (movieRequest.getName()).ifPresent(r -> {
                    throw new ResourceNotFoundException("Movie Name already exists");
                });

        Movie movie = Movie.builder()
                .name(movieRequest.getName())
                .duration(movieRequest.getDuration())
                .language(movieRequest.getLanguage())
                 .build();

        return mapToResponse(movieRepository.save(movie));
    }

    @Override
    public Movie getMovieById(Long movieId) throws ResourceNotFoundException{
        return movieRepository.findById(movieId)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found"));
    }

    @Override
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    @Override
    public MovieResponseDTO updateMovie(Long movieId, MovieRequestDTO movieRequest) {
        Movie existingMovie = getMovieById(movieId);

        // Check duplicate name for different movieId
        if (movieRepository.existsByNameIgnoreCaseAndMovieIdNot(
                movieRequest.getName(), movieId)) {

            throw new ResourceNotFoundException(
                    "Movie name already exists: " + movieRequest.getName());
        }

        existingMovie.setName(movieRequest.getName());
        existingMovie.setLanguage(movieRequest.getLanguage());
        existingMovie.setDuration(movieRequest.getDuration());
        return mapToResponse(movieRepository.save(existingMovie));
    }

    @Override
    public void deleteMovie(Long movieId) {
        Movie movie = getMovieById(movieId);
        movieRepository.delete(movie);
    }

    private MovieResponseDTO mapToResponse(Movie movie) {
        return new MovieResponseDTO(
                movie.getMovieId(),
                movie.getName(),
                movie.getDuration(),
                movie.getLanguage()
        );
       }
}
