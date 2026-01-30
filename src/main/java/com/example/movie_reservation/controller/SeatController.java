package com.example.movie_reservation.controller;

import com.example.movie_reservation.model.Seat;
import com.example.movie_reservation.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seats")
@RequiredArgsConstructor
public class SeatController {

    private final SeatService seatService;

    @PostMapping
    public Seat createSeat(@RequestBody Seat seat) {

        return seatService.createSeat(seat);
    }

    @GetMapping("/{id}")
    public Seat getSeat(@PathVariable Long id) {

        return seatService.getSeatById(id);
    }

    @GetMapping
    public List<Seat> getAllSeats() {

        return seatService.getAllSeats();
    }

    @GetMapping("/screen/{screenId}")
    public List<Seat> getSeatsByScreen(@PathVariable Long screenId) {

        return seatService.getSeatsByScreen(screenId);
    }

    @PutMapping("/{id}")
    public Seat updateSeat(
            @PathVariable Long id,
            @RequestBody Seat seat) {
        return seatService.updateSeat(id, seat);
    }

    @DeleteMapping("/{id}")
    public String deleteSeat(@PathVariable Long id) {
        seatService.deleteSeat(id);
        return "Seat deleted successfully";
    }
}