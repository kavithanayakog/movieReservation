package com.example.movie_reservation.service;

import com.example.movie_reservation.model.Booking;
import com.example.movie_reservation.responseDTO.BookingResponseDTO;

import java.util.List;

public interface BookingService {

    BookingResponseDTO createBooking(Long userId, Long showId, List<Long> seatIds);

    Booking getBookingById(Long bookingId);

    List<Booking> getAllBookings();

    BookingResponseDTO cancelBooking(Long bookingId);

    void deleteBooking(Long bookingId);
}
