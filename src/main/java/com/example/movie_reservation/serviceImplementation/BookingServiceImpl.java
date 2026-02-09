package com.example.movie_reservation.serviceImplementation;


import com.example.movie_reservation.model.*;
import com.example.movie_reservation.repository.*;
import com.example.movie_reservation.responseDTO.BookingResponseDTO;
import com.example.movie_reservation.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final BookingSeatRepository bookingSeatRepository;
    private final ShowSeatRepository showSeatRepository;
    private final UserRepository userRepository;
    private final ShowTimeRepository showTimeRepository;

    @Override
    public BookingResponseDTO createBooking(Long userId, Long showId, List<Long> seatIds) {

        // 1️⃣ Validate User
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 2️⃣ Validate Show
        ShowTime show = showTimeRepository.findById(showId)
                .orElseThrow(() -> new RuntimeException("Show not found"));

        // 3️⃣ Validate Seats Exist for Show
        List<ShowSeat> showSeats =
                showSeatRepository.findByShow_ShowIdAndSeat_SeatIdIn(showId, seatIds);

        if (showSeats.size() != seatIds.size()) {
            throw new RuntimeException("Invalid seats selected");
        }

        // 4️⃣ Validate Seat Availability
        for (ShowSeat ss : showSeats) {
            if (!ss.getIsAvailable()) {
                throw new RuntimeException("One or more seats already booked");
            }
        }

        // 5️⃣ Calculate Amount
        BigDecimal totalAmount = showSeats.stream()
                .map(ss -> ss.getSeat().getSeatType().getAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 6️⃣ Create Booking
        Booking booking = Booking.builder()
                .bookingTime(LocalDateTime.now())
                .status("CONFIRMED")
                .amount(totalAmount)
                .user(user)
                .show(show)
                .build();

        bookingRepository.save(booking);

         //Lock Seats + Create BookingSeat
        for (ShowSeat ss : showSeats) {
            ss.setIsAvailable(false);

            BookingSeat bookingSeat = BookingSeat.builder()
                    .booking(booking)
                    .seat(ss.getSeat())
                    .build();

            bookingSeatRepository.save(bookingSeat);
        }

        return mapToDTO(booking);
    }

    @Override
    public Booking getBookingById(Long bookingId) {
        return bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
    }

    @Override
    public List<Booking> getAllBookings() {

        return bookingRepository.findAll();
    }

    @Override
    public BookingResponseDTO cancelBooking(Long bookingId) {

        Booking booking = getBookingById(bookingId);

        if ("CANCELLED".equals(booking.getStatus())) {
            throw new RuntimeException("Booking already cancelled");
        }

        booking.setStatus("CANCELLED");

        List<ShowSeat> showSeats =
                showSeatRepository.findByShow_ShowId(booking.getShow().getShowId());

        System.out.println("List" + showSeats);

        for (ShowSeat ss : showSeats) {
            System.out.println("List" + showSeats.size());
            ss.setIsAvailable(true);
        }

        return mapToDTO(bookingRepository.save(booking));
    }

    @Override
    public void deleteBooking(Long bookingId) {
        Booking booking = getBookingById(bookingId);
        bookingRepository.delete(booking);
    }

    private BookingResponseDTO mapToDTO(Booking booking) {
        return new BookingResponseDTO(
                booking.getBookingId(),
                booking.getBookingTime(),
                booking.getStatus(),
                booking.getAmount(),
                booking.getCreatedDate(),
                booking.getUpdatedDate(),
                booking.getUser().getName(),
                booking.getShow().getMovie().getName()
                );
    }

}
