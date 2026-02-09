package com.example.movie_reservation.requestDTO;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
public class ScreenRequestDTO {

    @NotBlank
    private String screenName;

    @NotBlank
    private Integer totalSeatCount;

    @NotBlank
    private LocalDateTime createdDate;

    @NotBlank
    private LocalDateTime updatedDate;

    @NotBlank
    private Long theatre_id;
}
