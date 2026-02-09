package com.example.movie_reservation.requestDTO;


import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Data
public class ShowSeatRequestDTO {

    @NotBlank
    private Boolean isAvailable;

    @NotBlank
    private LocalDateTime createdDate;

    @NotBlank
    private LocalDateTime updatedDate;

    @NotBlank
    private Long showId;

    @NotBlank
    private Long seatId;
}
