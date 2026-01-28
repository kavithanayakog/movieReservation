package com.example.movie_reservation.controller;

import com.example.movie_reservation.model.Movie;
import com.example.movie_reservation.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/Movie")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @PostMapping
    public Movie createRole(@RequestBody Movie movie) {
        System.out.println("Creating movie: " + movie);
        return movieService.createMovie(movie);
    }

    @GetMapping("/{id}")
    public Movie getMovie(@PathVariable Long id) {

        return movieService.getMovieById(id);
    }

    @GetMapping
    public List<Movie> getAllMovies() {

        return movieService.getAllMovies();
    }

    @PutMapping("/{id}")
    public Movie updateRole(
            @PathVariable Long id,
            @RequestBody Movie movie) {
        return movieService.updateMovie(id, movie);
    }

    @DeleteMapping("/{id}")
    public String deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return "Movie deleted successfully";
    }
}
