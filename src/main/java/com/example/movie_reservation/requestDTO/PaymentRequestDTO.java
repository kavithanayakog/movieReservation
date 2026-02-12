package com.example.movie_reservation.requestDTO;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PaymentRequestDTO {

   /* @NotBlank
    private BigDecimal baseAmount;
    @NotBlank
    private BigDecimal taxAmount;  */
    @NotBlank
    private BigDecimal totalAmount;
    @NotBlank
    private String paymentMode;   // UPI, CARD, NET_BANKING, WALLET
    @NotBlank
    private String paymentStatus; // PENDING, SUCCESS, FAILED
    @NotBlank
    private LocalDateTime paymentTime;
    @NotBlank
    private LocalDateTime createdDate;
    @NotBlank
    private LocalDateTime updatedDate;
    @NotBlank
    private Long bookingId;
}
