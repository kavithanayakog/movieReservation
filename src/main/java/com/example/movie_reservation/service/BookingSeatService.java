package com.example.movie_reservation.service;

import com.example.movie_reservation.model.BookingSeat;

import java.util.List;

public interface BookingSeatService {

    BookingSeat createBookingSeat(Long bookingId, Long seatId);

    BookingSeat getBookingSeatById(Long bookingSeatId);

    List<BookingSeat> getBookingSeatsByBooking(Long bookingId);

    void deleteBookingSeat(Long bookingSeatId);
}
