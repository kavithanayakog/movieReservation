package com.example.movie_reservation.service;

import com.example.movie_reservation.model.Payment;
import com.example.movie_reservation.requestDTO.PaymentRequestDTO;
import com.example.movie_reservation.responseDTO.PaymentResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PaymentService {

    PaymentResponseDTO createPayment(PaymentRequestDTO payment);
    List<PaymentResponseDTO> getAllPayments();
    List<PaymentResponseDTO> getPaymentsByBooking(Long bookingId);
    PaymentResponseDTO updatePaymentStatus(Long paymentId, String status);
    void deletePayment(Long paymentId);

    PaymentResponseDTO getPaymentById(Long paymentId);
}
