package com.example.movie_reservation.responseDTO;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class PaymentResponseDTO {
    private Long paymentId;
   // private BigDecimal baseAmount;
   // private BigDecimal taxAmount;
    private BigDecimal totalAmount;
    private String paymentMode;   // UPI, CARD, NET_BANKING, WALLET
    private String paymentStatus; // PENDING, SUCCESS, FAILED
    private LocalDateTime paymentTime;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private Long bookingId;
    private String bookingStatus;
}
