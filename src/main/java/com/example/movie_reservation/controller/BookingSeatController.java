package com.example.movie_reservation.controller;

import com.example.movie_reservation.model.BookingSeat;
import com.example.movie_reservation.responseDTO.BookingResponseDTO;
import com.example.movie_reservation.responseDTO.BookingSeatResponseDTO;
import com.example.movie_reservation.service.BookingSeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/booking-seats")
@RequiredArgsConstructor
public class BookingSeatController {

    private final BookingSeatService bookingSeatService;

    @PostMapping
    public BookingSeatResponseDTO createBookingSeat(
            @RequestParam Long bookingId,
            @RequestParam Long seatId) {

        return bookingSeatService.createBookingSeat(bookingId, seatId);
    }

    @GetMapping("/{id}")
    public BookingSeatResponseDTO getBookingSeat(@PathVariable Long id) {

        return bookingSeatService.getBookingSeatById(id);
    }

    @GetMapping("/booking/{bookingId}")
    public List<BookingSeatResponseDTO> getSeatsByBooking(@PathVariable Long bookingId) {
        return bookingSeatService.getBookingSeatsByBooking(bookingId);
    }

    @DeleteMapping("/{id}")
    public String deleteBookingSeat(@PathVariable Long id) {
        bookingSeatService.deleteBookingSeat(id);
        return "BookingSeat deleted successfully";
    }


}
