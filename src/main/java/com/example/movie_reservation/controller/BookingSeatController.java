package com.example.movie_reservation.controller;

import com.example.movie_reservation.model.BookingSeat;
import com.example.movie_reservation.service.BookingSeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/booking-seats")
@RequiredArgsConstructor
public class BookingSeatController {

    private final BookingSeatService bookingSeatService;

    // CREATE
    @PostMapping
    public BookingSeat createBookingSeat(
            @RequestParam Long bookingId,
            @RequestParam Long seatId) {

        return bookingSeatService.createBookingSeat(bookingId, seatId);
    }

    // READ by ID
    @GetMapping("/{id}")
    public BookingSeat getBookingSeat(@PathVariable Long id) {
        return bookingSeatService.getBookingSeatById(id);
    }

    // READ by Booking
    @GetMapping("/booking/{bookingId}")
    public List<BookingSeat> getSeatsByBooking(@PathVariable Long bookingId) {
        return bookingSeatService.getBookingSeatsByBooking(bookingId);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public String deleteBookingSeat(@PathVariable Long id) {
        bookingSeatService.deleteBookingSeat(id);
        return "BookingSeat deleted successfully";
    }
}
