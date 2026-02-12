package com.example.movie_reservation.responseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class BookingSeatResponseDTO {
    private Long bookingSeatId;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private Long bookingId;
    private String bookingStatus;
    private String seatNumber;
    private String seatType;

}
