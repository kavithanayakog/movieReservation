package com.example.movie_reservation.service;

import com.example.movie_reservation.model.BookingSeat;
import com.example.movie_reservation.responseDTO.BookingSeatResponseDTO;

import java.util.List;

public interface BookingSeatService {

    BookingSeatResponseDTO createBookingSeat(Long bookingId, Long seatId);

    BookingSeatResponseDTO getBookingSeatById(Long bookingSeatId);

    List<BookingSeatResponseDTO> getBookingSeatsByBooking(Long bookingId);

    void deleteBookingSeat(Long bookingSeatId);
}
