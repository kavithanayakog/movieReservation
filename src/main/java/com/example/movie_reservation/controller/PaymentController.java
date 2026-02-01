package com.example.movie_reservation.controller;

import com.example.movie_reservation.model.Payment;
import com.example.movie_reservation.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public Payment processPayment(@RequestBody Payment payment) {
        // Placeholder for payment processing logic
        return paymentService.createPayment(payment);
    }

    @GetMapping("/{paymentId}")
    public Payment getPaymentDetails(@PathVariable Long paymentId) {
        return paymentService.getPaymentById(paymentId);
    }

    @GetMapping("/booking/{bookingId}")
    public List<Payment> getPaymentByUser(@PathVariable Long bookingId) {
        return paymentService.getPaymentsByBooking(bookingId);
    }

    @PutMapping("/{id}/status")
    public Payment updatePaymentStatus(@PathVariable Long id, @RequestParam  String status) {
        return paymentService.updatePaymentStatus(id, status);
    }

    @DeleteMapping("/{id}")
    public String refundPayment(@PathVariable Long id) {
        paymentService.deletePayment(id);
        return "Payment deleted successfully";
    }
}
