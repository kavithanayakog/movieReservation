package com.example.movie_reservation.repository;

import com.example.movie_reservation.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking,Long> {

    void deleteById(Long bookingId);
}
