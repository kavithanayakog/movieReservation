package com.example.movie_reservation.repository;

import com.example.movie_reservation.model.SeatType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SeatTypeRepository extends JpaRepository<SeatType, Long> {

    boolean existsBySeatTypeNameIgnoreCase(String seatTypeName);

    Optional<SeatType> findBySeatTypeNameIgnoreCase(String seatTypeName);
}
