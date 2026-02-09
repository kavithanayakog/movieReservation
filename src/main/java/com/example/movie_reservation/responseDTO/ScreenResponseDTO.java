package com.example.movie_reservation.responseDTO;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ScreenResponseDTO {

    private Long screenId;
    private String screenName;
    private Integer totalSeatCount;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private String theatreName;
}
