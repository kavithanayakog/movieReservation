package com.example.movie_reservation.serviceImplementation;

import com.example.movie_reservation.model.Booking;
import com.example.movie_reservation.model.Payment;
import com.example.movie_reservation.repository.BookingRepository;
import com.example.movie_reservation.repository.PaymentRepository;
import com.example.movie_reservation.service.PaymentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;

    private static final List<String> VALID_MODES =
            List.of("UPI", "CARD", "NET_BANKING");

    private static final List<String> VALID_STATUS =
            List.of("PENDING", "SUCCESS", "FAILED");

    @Override
    public Payment createPayment(Payment payment) {

        Long bookingId = payment.getBooking().getBookingId();
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        paymentRepository
                .findByBooking_BookingIdAndPaymentStatus(bookingId, "SUCCESS")
                .ifPresent(p -> {
                    throw new RuntimeException(
                            "Payment already completed for this booking");
                });

        BigDecimal calculated =
                payment.getBaseAmount().add(payment.getTaxAmount());

        if (calculated.compareTo(payment.getTotalAmount()) != 0) {
            throw new RuntimeException(
                    "Total amount must be base + tax");
        }

        // 4️⃣ Validate payment mode
        if (!VALID_MODES.contains(payment.getPaymentMode())) {
            throw new RuntimeException("Invalid payment mode");
        }

        // 5️⃣ Validate payment status
        if (!VALID_STATUS.contains(payment.getPaymentStatus())) {
            throw new RuntimeException("Invalid payment status");
        }

        // 6️⃣ Set payment time if SUCCESS
        if ("SUCCESS".equals(payment.getPaymentStatus())) {
            payment.setPaymentTime(LocalDateTime.now());
        }

        payment.setBooking(booking);
        return paymentRepository.save(payment);
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    @Override
    public List<Payment> getPaymentsByBooking(Long bookingId) {

        if(!bookingRepository.existsById(bookingId)){
            throw new RuntimeException("Booking not found");
        }
        return paymentRepository.findByBooking_BookingId(bookingId);
    }

    @Override
    public Payment updatePaymentStatus(Long paymentId, String status) {
        Payment payment = getPaymentById(paymentId);

        if("SUCCESS".equals(payment.getPaymentStatus())){
            throw new RuntimeException("Cannot update a successful payment");
        }

        if(!"SUCCESS".equals(status) && !"FAILED".equals(status)){
            throw new RuntimeException("Invalid payment status");
        }

        payment.setPaymentStatus(status);

        if("SUCCESS".equals(status)){
            payment.setPaymentTime(LocalDateTime.now());
        }
        return paymentRepository.save(payment);

    }

    public Payment getPaymentById(Long paymentId) {
        return paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
    }

    @Override
    public void deletePayment(Long paymentId) {
        Payment payment = getPaymentById(paymentId);

        if("SUCCESS".equals(payment.getPaymentStatus())){
            throw new RuntimeException("Cannot delete a successful payment");
        }

        paymentRepository.delete(payment);
    }


}
