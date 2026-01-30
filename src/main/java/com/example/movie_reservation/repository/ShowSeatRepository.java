package com.example.movie_reservation.repository;

import com.example.movie_reservation.model.ShowSeat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShowSeatRepository extends JpaRepository<ShowSeat, Long> {

    List<ShowSeat> findByShow_ShowIdAndSeat_SeatIdIn(Long showId, List<Long> seatIds);

    Optional<ShowSeat> findByShow_ShowIdAndSeat_SeatId(Long showId, Long seatId);

    boolean existsByShow_ShowIdAndSeat_SeatId(Long showId, Long seatId);

    List<ShowSeat> findByShow_ShowId(Long showId);

}
