package com.example.movie_reservation.repository;

import com.example.movie_reservation.model.ShowTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface ShowTimeRepository extends JpaRepository<ShowTime, Long> {

    @Query("""
        SELECT s FROM ShowTime s
        WHERE s.screen.screenId = :screenId
        AND (
            (:startTime < s.endTime AND :endTime > s.startTime)
        )
    """)
    List<ShowTime> findOverlappingShows(
            Long screenId,
            LocalDateTime startTime,
            LocalDateTime endTime
    );

    List<ShowTime> findByMovie_MovieId(Long movieId);

    List<ShowTime> findByScreen_ScreenId(Long screenId);

}
