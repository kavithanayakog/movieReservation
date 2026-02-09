package com.example.movie_reservation.responseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ShowSeatResponseDTO {

    private Long showSeatId;
    private Boolean isAvailable;
    private String createdDate;
    private String updatedDate;
    private Long show;
    private String seat_number;

}
