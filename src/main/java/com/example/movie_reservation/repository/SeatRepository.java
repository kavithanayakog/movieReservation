package com.example.movie_reservation.repository;

import com.example.movie_reservation.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat,Long> {

    List<Seat> findByScreen_ScreenId(Long showId);

    boolean existsBySeatNumberAndScreen_ScreenId(String seatNumber, Long screenId);
}
