package com.example.movie_reservation.repository;

import com.example.movie_reservation.model.Seat;
import com.example.movie_reservation.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface SeatRepository extends JpaRepository<Seat,Long> {

    List<Seat> findByScreen_ScreenId(Long showId);

    boolean existsBySeatNumberAndScreen_ScreenId(String seatNumber, Long screenId);

    boolean existsBySeatType_SeatTypeId(Long seatTypeId);

    boolean existsByScreen_ScreenId(Long screenId);

    List<Seat> findAllByScreen_ScreenId(Long screenId);
}
