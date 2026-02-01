package com.example.movie_reservation.service;

import com.example.movie_reservation.model.Payment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PaymentService {

    Payment createPayment(Payment payment);
    List<Payment> getAllPayments();
    List<Payment> getPaymentsByBooking(Long bookingId);
    Payment updatePaymentStatus(Long paymentId, String status);
    void deletePayment(Long paymentId);

    Payment getPaymentById(Long paymentId);
}
