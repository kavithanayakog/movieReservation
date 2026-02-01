package com.example.movie_reservation.controller;


import com.example.movie_reservation.model.SeatType;
import com.example.movie_reservation.service.SeatTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/seat-types")
public class SeatTypeController {

    private final SeatTypeService seatTypeService;

    @PostMapping
    public SeatType createSeatType(@RequestBody SeatType seatType) {
        return seatTypeService.createSeatType(seatType);

    }

    @GetMapping("/{id}")
    public SeatType getSeatTypeById(@PathVariable Long id) {
        return seatTypeService.getSeatTypeById(id);
    }

    @GetMapping
    public List<SeatType> getAllSeatTypes() {
        return seatTypeService.getAllSeatTypes();
    }

    @PutMapping("/{id}")
    public SeatType updateSeatType(@PathVariable Long id, @RequestBody SeatType seatType) {
        return seatTypeService.updateSeatType(id, seatType);
    }

    @DeleteMapping("/{id}")
    public String deleteSeatType(@PathVariable Long id) {
        seatTypeService.deleteSeatType(id);
        return "Seat type deleted successfully";
    }
}
