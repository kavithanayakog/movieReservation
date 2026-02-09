package com.example.movie_reservation.requestDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TheatreRequestDTO {

    @NotBlank
    private String name;

    @NotBlank
    private String city;

    private String state;

    private String location;

    @NotBlank
    private LocalDateTime createdDate;

    @NotBlank
    private LocalDateTime updatedDate;
}
