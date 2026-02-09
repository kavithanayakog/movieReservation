package com.example.movie_reservation.responseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MovieResponseDTO {
    private Long movieId;
    private String name;
    private Integer duration; // duration in minutes
    private String language;
}
