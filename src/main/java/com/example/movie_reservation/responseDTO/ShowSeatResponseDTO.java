package com.example.movie_reservation.responseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ShowSeatResponseDTO {

    private Long showSeatId;
    private Boolean isAvailable;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private Long show;
    private String seat_number;

}
