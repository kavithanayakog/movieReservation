package com.example.movie_reservation.controller;

import com.example.movie_reservation.model.Seat;
import com.example.movie_reservation.requestDTO.SeatRequestDTO;
import com.example.movie_reservation.responseDTO.SeatResponseDTO;
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
    public SeatResponseDTO createSeat(@RequestBody SeatRequestDTO seat) {

        return seatService.createSeat(seat);
    }

    @GetMapping("/{id}")
    public SeatResponseDTO getSeat(@PathVariable Long id) {

        return seatService.getSeatById(id);
    }

    @GetMapping
    public List<SeatResponseDTO> getAllSeats() {

        return seatService.getAllSeats();
    }

    @GetMapping("/screen/{screenId}")
    public List<SeatResponseDTO> getSeatsByScreen(@PathVariable Long screenId) {

        return seatService.getSeatsByScreen(screenId);
    }

    @PutMapping("/{id}")
    public SeatResponseDTO updateSeat(
            @PathVariable Long id,
            @RequestBody SeatRequestDTO seat) {
        return seatService.updateSeat(id, seat);
    }

    @DeleteMapping("/{id}")
    public String deleteSeat(@PathVariable Long id) {
        seatService.deleteSeat(id);
        return "Seat deleted successfully";
    }
}