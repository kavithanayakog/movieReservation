package com.example.movie_reservation.controller;

import com.example.movie_reservation.model.Booking;
import com.example.movie_reservation.requestDTO.BookingSeatListDTO;
import com.example.movie_reservation.responseDTO.BookingResponseDTO;
import com.example.movie_reservation.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public BookingResponseDTO createBooking(
            @RequestParam Long userId,
            @RequestParam Long showId, BookingSeatListDTO seatIdRequest){
            //@RequestBody List<Long> seatIds) {

        return bookingService.createBooking(userId, showId, seatIdRequest.getSeatIds());
    }

    @GetMapping("/{id}")
    public BookingResponseDTO getBookingById(@PathVariable Long id) {

        return bookingService.getBookingById(id);
    }

    @GetMapping
    public List<BookingResponseDTO> getAllBookings() {

        return bookingService.getAllBookings();
    }

    @PutMapping("/{id}/cancel")
    public BookingResponseDTO cancelBooking(@PathVariable Long id) {
        return bookingService.cancelBooking(id);
    }

    @DeleteMapping("/{id}")
    public String deleteBooking(@PathVariable Long id) {
        bookingService.deleteBooking(id);
        return "Booking deleted successfully";
    }
}
