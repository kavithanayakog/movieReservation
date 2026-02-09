package com.example.movie_reservation.responseDTO;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
@AllArgsConstructor
public class BookingResponseDTO {
    private Long bookingId;
    private LocalDateTime bookingTime;
    private String status;   // PENDING, CONFIRMED, CANCELLED
    private BigDecimal amount;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private String userName;
    private String showName;
}
