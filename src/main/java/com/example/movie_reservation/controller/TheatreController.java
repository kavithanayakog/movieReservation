package com.example.movie_reservation.controller;

import com.example.movie_reservation.model.Theatre;
import com.example.movie_reservation.service.TheatreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/theatres")
@RequiredArgsConstructor
public class TheatreController {

    private final TheatreService theatreService;

    @PostMapping
    public Theatre createTheatre(@RequestBody Theatre theatre) {
        return theatreService.createTheatre(theatre);
    }

    @GetMapping("/{id}")
    public Theatre getTheatre(@PathVariable Long id) {
        return theatreService.getTheatreById(id);
    }

    @GetMapping
    public List<Theatre> getAllTheatres() {
        return theatreService.getAllTheatres();
    }

    @PutMapping("/{id}")
    public Theatre updateTheatre(
            @PathVariable Long id,
            @RequestBody Theatre theatre) {
        return theatreService.updateTheatre(id, theatre);
    }

    @DeleteMapping("/{id}")
    public String deleteTheatre(@PathVariable Long id) {
        theatreService.deleteTheatre(id);
        return "Theatre deleted successfully";
    }
}