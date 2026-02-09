package com.example.movie_reservation.requestDTO;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class BookingRequestDTO {

    private LocalDateTime bookingTime;
    private String status;   // PENDING, CONFIRMED, CANCELLED
    private BigDecimal amount;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private Long userId;
    private Long showId;
}
