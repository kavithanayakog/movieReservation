package com.example.movie_reservation.serviceImplementation;

import com.example.movie_reservation.exception.ResourceNotFoundException;
import com.example.movie_reservation.model.*;
import com.example.movie_reservation.repository.*;
import com.example.movie_reservation.requestDTO.ShowTimeRequestDTO;
import com.example.movie_reservation.responseDTO.ShowTimeResponseDTO;
import com.example.movie_reservation.service.ShowTimeService;
import lombok.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
    private final BookingRepository bookingRepository;
    private final BookingSeatRepository bookingSeatRepository;
    private final PaymentRepository paymentRepository;

    @Override
    public ShowTimeResponseDTO createShowTime(ShowTimeRequestDTO showTime) throws ResourceNotFoundException {

        Movie movie = movieRepository.findById(showTime.getMovieId())
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found"));

       Screen screen = screenRepository.findById(showTime.getScreenId())
                .orElseThrow(() -> new ResourceNotFoundException("Screen not found"));

        LocalDateTime startTime = showTime.getStartTime();
        LocalDateTime endTime = startTime.plusMinutes(movie.getDuration());

        if (!showTime.getStartTime().isBefore(endTime)) {
            throw new ResourceNotFoundException("Start time must be before end time");
        }
        List<ShowTime> conflicts = showTimeRepository.findOverlappingShows(
                screen.getScreenId(),
                showTime.getStartTime(),
                endTime,
                movie.getMovieId()
        );
        if (!conflicts.isEmpty()) {
            throw new ResourceNotFoundException("Another movie already scheduled for this screen at the same time");
        }

          ShowTime ShowTimeRequest = ShowTime.builder()
                .startTime(showTime.getStartTime())
                //.endTime(showTime.getEndTime())
                .endTime(endTime)
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
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
    public List<ShowTimeResponseDTO> getAllShowTime() {

        return showTimeRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public ShowTimeResponseDTO getShowTimeById(Long showTimeId) throws ResourceNotFoundException{
        ShowTime showTime =  showTimeRepository.findById(showTimeId)
                .orElseThrow(() -> new ResourceNotFoundException("ShowTime not found"));

        return mapToResponse(showTime);
    }

    @Override
    public ShowTimeResponseDTO updateShowTime(Long showTimeId, ShowTimeRequestDTO showTime) throws ResourceNotFoundException{
        ShowTime existing = showTimeRepository.findById(showTimeId)
                .orElseThrow(() -> new ResourceNotFoundException("ShowTime not found"));

        Movie movie = movieRepository.findById(showTime.getMovieId())
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found"));

        Screen screen = screenRepository.findById(showTime.getScreenId())
                .orElseThrow(() -> new ResourceNotFoundException("Screen not found"));

        LocalDateTime startTime = showTime.getStartTime();
        LocalDateTime endTime = startTime.plusMinutes(movie.getDuration());

        if (!showTime.getStartTime().isBefore(endTime)) {
            throw new ResourceNotFoundException("Invalid time range");
        }

        List<ShowTime> conflicts = showTimeRepository.findOverlappingShows(
                existing.getScreen().getScreenId(),
                showTime.getStartTime(),
                endTime,
                movie.getMovieId()
        );

        System.out.println("  conflicts "+ conflicts.size());

        if (!conflicts.isEmpty()) {
            System.out.println("  conflicts inside if "+ conflicts);
            throw new ResourceNotFoundException("Another movie already scheduled for this screen at the same time");
        }

        existing.setStartTime(showTime.getStartTime());
        existing.setEndTime(endTime);
        existing.setMovie(movie);
        existing.setScreen(screen);

        return mapToResponse(showTimeRepository.save(existing));
    }

    @Override
    public void deleteShowTime(Long showTimeId) throws ResourceNotFoundException{

        ShowTime showTime =  showTimeRepository.findById(showTimeId)
                .orElseThrow(() -> new ResourceNotFoundException("ShowTime not found"));

        Long showId = showTime.getShowId();
        List<Booking> bookings = bookingRepository.findByShow_ShowId(showId);

        for (Booking booking : bookings) {
            if (!booking.getStatus().equalsIgnoreCase("CANCELLED")) {
                throw new ResourceNotFoundException(
                        "Cannot delete show. Active bookings exist.");
            }
        }

        List<Long> bookingId = bookings.stream()
                .map(Booking::getBookingId)
                .toList();

       if (!bookingId.isEmpty()) {

            bookingSeatRepository.deleteByBooking_BookingIdIn(bookingId);

            paymentRepository.deleteByBooking_BookingIdIn(bookingId);

            bookingRepository.deleteAll(bookings);
        }
        showSeatRepository.deleteAll(
                showSeatRepository.findByShow_ShowId(showTimeId));

        showTimeRepository.delete(showTime);
    }

    @Override
    public List<ShowTimeResponseDTO> getShowsByMovie(Long movieId)  throws ResourceNotFoundException{

        if (!movieRepository.existsById(movieId)) {
            throw new ResourceNotFoundException("Movie not found");
        }

        return showTimeRepository.findByMovie_MovieId(movieId)
                .stream().map(this::mapToResponse)
                .toList();

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
