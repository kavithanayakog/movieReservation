package com.example.movie_reservation.controller;

import com.example.movie_reservation.model.ShowSeat;
import com.example.movie_reservation.service.ShowSeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/show-seats")
@RequiredArgsConstructor
public class ShowSeatController {

    private final ShowSeatService showSeatService;

    // CREATE
    @PostMapping
    public ShowSeat createShowSeat(
            @RequestParam Long showId,
            @RequestParam Long seatId) {
        return showSeatService.createShowSeat(showId, seatId);
    }

    // READ by ID
    @GetMapping("/{id}")
    public ShowSeat getShowSeat(@PathVariable Long id) {
        return showSeatService.getShowSeatById(id);
    }

    // READ by Show
    @GetMapping("/show/{showId}")
    public List<ShowSeat> getSeatsByShow(@PathVariable Long showId) {
        return showSeatService.getShowSeatsByShow(showId);
    }

    // UPDATE availability
    @PutMapping("/{id}/availability")
    public ShowSeat updateAvailability(
            @PathVariable Long id,
            @RequestParam Boolean isAvailable) {
        return showSeatService.updateAvailability(id, isAvailable);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public String deleteShowSeat(@PathVariable Long id) {
        showSeatService.deleteShowSeat(id);
        return "ShowSeat deleted successfully";
    }
}

