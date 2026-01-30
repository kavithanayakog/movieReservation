package com.example.movie_reservation.service;

import com.example.movie_reservation.model.Booking;

import java.util.List;

public interface BookingService {

    Booking createBooking(Long userId, Long showId, List<Long> seatIds);

    Booking getBookingById(Long bookingId);

    List<Booking> getAllBookings();

    Booking cancelBooking(Long bookingId);

    void deleteBooking(Long bookingId);
}
