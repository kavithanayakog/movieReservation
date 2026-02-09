package com.example.movie_reservation.requestDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MovieRequestDTO {

    @NotBlank
    private String name;

    @NotBlank
    private Integer duration; // duration in minutes

    @NotBlank
    private String language;
}
