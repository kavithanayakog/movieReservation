package com.example.movie_reservation.requestDTO;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class SeatTypeRequestDTO {

    @NotBlank
    private String seatTypeName;

    @NotBlank
    private BigDecimal amount;

    @NotBlank
    private LocalDateTime createdDate;

    @NotBlank
    private LocalDateTime updatedDate;
}
