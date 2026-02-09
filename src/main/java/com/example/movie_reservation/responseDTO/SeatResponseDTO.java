package com.example.movie_reservation.responseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class SeatResponseDTO {
    private Long seatId;
    private String seatNumber;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private String seatTypeName;
    private String screenName;
}
