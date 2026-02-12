package com.example.movie_reservation.requestDTO;


import lombok.Data;

import java.util.List;

@Data
public class BookingSeatListDTO {

    private List<Long> seatIds;
}
