package com.example.movie_reservation.responseDTO;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ShowTimeResponseDTO {
    private Long showId;
    private LocalDateTime startTime; // e.g., "2024-07-01T19:30:00"
    private LocalDateTime endTime;   // e.g., "2024-07-01T21:30:00"
    private String movieName;
    private String screenName;
}
