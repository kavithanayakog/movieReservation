package com.example.movie_reservation.repository;

import com.example.movie_reservation.model.BookingSeat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface BookingSeatRepository extends JpaRepository<BookingSeat, Long> {

    boolean existsByBooking_BookingIdAndSeat_SeatId(Long bookingId, Long seatId);

    List<BookingSeat> findByBooking_BookingId(Long bookingId);

    void deleteByBooking_BookingId(Long bookingId);

    void deleteByBooking_BookingIdIn(List<Long> bookingId);

    List<BookingSeat> findBySeat_SeatId(Long seatId);
}