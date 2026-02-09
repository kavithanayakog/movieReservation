package com.example.movie_reservation.responseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class TheatreResponseDTO {
    private Long theatreId;
    private String name;
    private String city;
    private String state;
    private String location;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
