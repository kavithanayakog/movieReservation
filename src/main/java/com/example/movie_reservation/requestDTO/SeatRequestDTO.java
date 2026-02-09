package com.example.movie_reservation.requestDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Data
public class SeatRequestDTO {

    @NotBlank
    private String seatNumber;

    @NotBlank
    private LocalDateTime createdDate;

    @NotBlank
    private LocalDateTime updatedDate;

    @NotBlank
    private Long seatType;

    @NotBlank
    private Long screen;

}
