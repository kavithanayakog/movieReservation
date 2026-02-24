package com.example.movie_reservation.repository;

import com.example.movie_reservation.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByBooking_BookingId(Long bookingId);

    Optional<Object> findByBooking_BookingIdAndPaymentStatus(Long bookingId, String success);

    void deleteByBooking_BookingId(Long bookingId);

    void deleteByBooking_BookingIdIn(List<Long> bookingId);
}
