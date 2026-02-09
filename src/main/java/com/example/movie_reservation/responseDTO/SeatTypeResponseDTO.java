package com.example.movie_reservation.responseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class SeatTypeResponseDTO {

    private Long seatTypeId;
    private String seatTypeName;
    private BigDecimal amount;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

}
