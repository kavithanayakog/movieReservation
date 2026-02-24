package com.example.movie_reservation.serviceImplementation;

import com.example.movie_reservation.exception.ResourceNotFoundException;
import com.example.movie_reservation.model.Booking;
import com.example.movie_reservation.model.Payment;
import com.example.movie_reservation.repository.BookingRepository;
import com.example.movie_reservation.repository.PaymentRepository;
import com.example.movie_reservation.requestDTO.PaymentRequestDTO;
import com.example.movie_reservation.responseDTO.PaymentResponseDTO;
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
    public PaymentResponseDTO createPayment(PaymentRequestDTO payment) throws ResourceNotFoundException {

        Long bookingId = payment.getBookingId();
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with ID  "+ bookingId));

        paymentRepository
                .findByBooking_BookingIdAndPaymentStatus(bookingId, "SUCCESS")
                .ifPresent(p -> {
                    throw new ResourceNotFoundException(
                            "Payment already completed for this booking");
                });

       //BigDecimal calculated = payment.getBaseAmount().add(payment.getTaxAmount());

    /*    if (calculated.compareTo(payment.getTotalAmount()) != 0) {
            throw new RuntimeException(
                    "Total amount must be base + tax");
        }  */

        // 4️⃣ Validate payment mode
        if (!VALID_MODES.contains(payment.getPaymentMode())) {
            throw new ResourceNotFoundException("Invalid payment mode");
        }

        System.out.println("Payment mode: " + payment.getPaymentStatus());

        System.out.println("Payment mode: " + VALID_STATUS);

        // 5️⃣ Validate payment status
        if (!VALID_STATUS.contains(payment.getPaymentStatus())) {
            throw new ResourceNotFoundException("Invalid payment status");
        }

        // 6️⃣ Set payment time if SUCCESS
        if ("SUCCESS".equals(payment.getPaymentStatus())) {
            payment.setPaymentTime(LocalDateTime.now());
        }

        //payment.setTotalAmount(booking.getAmount());
        //payment.setBookingId(booking.getBookingId());

        Payment paymentRequest = Payment.builder()
                .totalAmount(booking.getAmount())
                .paymentMode(payment.getPaymentMode())
                .paymentStatus(payment.getPaymentStatus())
                .paymentTime(payment.getPaymentTime())
                .createdDate(payment.getCreatedDate())
                .updatedDate(payment.getUpdatedDate())
                .booking(booking)
                .build();

        paymentRepository.save(paymentRequest);

        return mapToDTO(paymentRequest);
    }

    @Override
    public List<PaymentResponseDTO> getAllPayments() {

        return paymentRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();

    }

    @Override
    public List<PaymentResponseDTO> getPaymentsByBooking(Long bookingId) {


        if(!bookingRepository.existsById(bookingId)){
            throw new ResourceNotFoundException("Booking not found with ID "+ bookingId);
        }

        return paymentRepository.findByBooking_BookingId(bookingId)
                .stream()
                .map(this::mapToDTO)
                .toList();

        //List<Payment> payment = paymentRepository.findByBooking_BookingId(bookingId);
       // return mapToDTO(payment);
    }

    @Override
    public PaymentResponseDTO updatePaymentStatus(Long paymentId, String status) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with ID "+ paymentId));

        if("SUCCESS".equals(payment.getPaymentStatus())){
            throw new ResourceNotFoundException("Cannot update a successful payment");
        }

        if(!"SUCCESS".equals(status) && !"FAILED".equals(status)){
            throw new ResourceNotFoundException("Invalid payment status");
        }

        payment.setPaymentStatus(status);

        if("SUCCESS".equals(status)){
            payment.setPaymentTime(LocalDateTime.now());
        }
        return mapToDTO(paymentRepository.save(payment));

    }

    public PaymentResponseDTO getPaymentById(Long paymentId) {
        return mapToDTO(paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found")));


    }

    @Override
    public void deletePayment(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found"));

        if("SUCCESS".equals(payment.getPaymentStatus())){
            throw new ResourceNotFoundException("Cannot delete a successful payment");
        }

        paymentRepository.delete(payment);
    }

    private PaymentResponseDTO mapToDTO(Payment payment){
        return new PaymentResponseDTO(
                payment.getPaymentId(),
                payment.getTotalAmount(),
                payment.getPaymentMode(),
                payment.getPaymentStatus(),
                payment.getPaymentTime(),
                payment.getCreatedDate(),
                payment.getUpdatedDate(),
                payment.getBooking().getBookingId(),
                payment.getBooking().getStatus()
        );
    }


}
