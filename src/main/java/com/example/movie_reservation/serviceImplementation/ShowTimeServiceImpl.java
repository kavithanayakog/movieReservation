package com.example.movie_reservation.serviceImplementation;

import com.example.movie_reservation.model.*;
import com.example.movie_reservation.repository.*;
import com.example.movie_reservation.requestDTO.ShowTimeRequestDTO;
import com.example.movie_reservation.responseDTO.ShowTimeResponseDTO;
import com.example.movie_reservation.service.ShowTimeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ShowTimeServiceImpl implements ShowTimeService {

    private final ShowTimeRepository showTimeRepository;
    private final ShowSeatRepository showSeatRepository;
    private final MovieRepository movieRepository;
    private final ScreenRepository screenRepository;
    private  final SeatRepository seatRepository;

    @Override
    public ShowTimeResponseDTO createShowTime(ShowTimeRequestDTO showTime) {

        Movie movie = movieRepository.findById(showTime.getMovieId())
                .orElseThrow(() -> new RuntimeException("Movie not found"));

       Screen screen = screenRepository.findById(showTime.getScreenId())
                .orElseThrow(() -> new RuntimeException("Screen not found"));

        if (!showTime.getStartTime().isBefore(showTime.getEndTime())) {
            throw new RuntimeException("Start time must be before end time");
        }
        List<ShowTime> conflicts = showTimeRepository.findOverlappingShows(
                screen.getScreenId(),
                showTime.getStartTime(),
                showTime.getEndTime()
        );
        if (!conflicts.isEmpty()) {
            throw new RuntimeException("Show time overlaps with another show");
        }

        ShowTime ShowTimeRequest = ShowTime.builder()
                .startTime(showTime.getStartTime())
                .endTime(showTime.getEndTime())
                .createdDate(showTime.getCreatedDate())
                .updatedDate(showTime.getUpdatedDate())
                .movie(movie)
                .screen(screen)
                .build();

        ShowTime savedShow = showTimeRepository.save(ShowTimeRequest);

        // Auto-generate ShowSeats
        List<Seat> seats = seatRepository.findByScreen_ScreenId(
                screen.getScreenId());

        for (Seat seat : seats) {
            ShowSeat showSeat = ShowSeat.builder()
                    .show(savedShow)
                    .seat(seat)
                    .isAvailable(true)
                    .build();
            showSeatRepository.save(showSeat);
        }

        return mapToResponse(savedShow);
    }

    @Override
    public List<ShowTime> getAllShowTime() {
        return showTimeRepository.findAll();
    }

    @Override
    public ShowTime getShowTimeById(Long showTimeId) {
        return showTimeRepository.findById(showTimeId)
                .orElseThrow(() -> new RuntimeException("ShowTime not found"));
    }

    @Override
    public ShowTimeResponseDTO updateShowTime(Long showTimeId, ShowTimeRequestDTO showTime) {
        ShowTime existing = getShowTimeById(showTimeId);

        Movie movie = movieRepository.findById(showTime.getMovieId())
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        Screen screen = screenRepository.findById(showTime.getScreenId())
                .orElseThrow(() -> new RuntimeException("Screen not found"));

        if (!showTime.getStartTime().isBefore(showTime.getEndTime())) {
            throw new RuntimeException("Invalid time range");
        }

        List<ShowTime> conflicts = showTimeRepository.findOverlappingShows(
                existing.getScreen().getScreenId(),
                showTime.getStartTime(),
                showTime.getEndTime()
        );

        if (!conflicts.isEmpty()) {
            throw new RuntimeException("Updated time overlaps with another show");
        }

        existing.setStartTime(showTime.getStartTime());
        existing.setEndTime(showTime.getEndTime());
        existing.setMovie(movie);
        existing.setScreen(screen);

        return mapToResponse(showTimeRepository.save(existing));
    }

    @Override
    public void deleteShowTime(Long showTimeId) {

        ShowTime showTime = getShowTimeById(showTimeId);
        showSeatRepository.deleteAll(
                showSeatRepository.findByShow_ShowId(showTimeId));

        showTimeRepository.delete(showTime);
    }

    @Override
    public List<ShowTime> getShowsByMovie(Long movieId) {

        if (!movieRepository.existsById(movieId)) {
            throw new RuntimeException("Movie not found");
        }

        return showTimeRepository.findByMovie_MovieId(movieId);

    }

    private ShowTimeResponseDTO mapToResponse(ShowTime showTime) {
        return new ShowTimeResponseDTO(
                showTime.getShowId(),
                showTime.getStartTime(),
                showTime.getEndTime(),
                showTime.getMovie().getName(),
                showTime.getScreen().getScreenName()
        );
    }
}
