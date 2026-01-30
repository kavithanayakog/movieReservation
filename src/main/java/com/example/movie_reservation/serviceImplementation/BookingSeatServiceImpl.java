package com.example.movie_reservation.serviceImplementation;

import com.example.movie_reservation.model.Booking;
import com.example.movie_reservation.model.BookingSeat;
import com.example.movie_reservation.model.Seat;
import com.example.movie_reservation.model.ShowSeat;
import com.example.movie_reservation.repository.BookingRepository;
import com.example.movie_reservation.repository.BookingSeatRepository;
import com.example.movie_reservation.repository.SeatRepository;
import com.example.movie_reservation.repository.ShowSeatRepository;
import com.example.movie_reservation.service.BookingSeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BookingSeatServiceImpl implements BookingSeatService {

    private final BookingSeatRepository bookingSeatRepository;
    private final BookingRepository bookingRepository;
    private final SeatRepository seatRepository;
    private final ShowSeatRepository showSeatRepository;

    @Override
    public BookingSeat createBookingSeat(Long bookingId, Long seatId) {

        // 1️⃣ Validate Booking exists
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        // 2️⃣ Validate Seat exists
        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(() -> new RuntimeException("Seat not found"));

        // 3️⃣ Prevent duplicate booking-seat mapping
        if (bookingSeatRepository
                .existsByBooking_BookingIdAndSeat_SeatId(bookingId, seatId)) {
            throw new RuntimeException("Seat already added to this booking");
        }

        // 4️⃣ Validate Seat belongs to the same show as Booking
        Long showId = booking.getShow().getShowId();

        ShowSeat showSeat = showSeatRepository
                .findByShow_ShowIdAndSeat_SeatId(showId, seatId)
                .orElseThrow(() -> new RuntimeException(
                        "Seat does not belong to this show"));

        // 5️⃣ Validate Seat availability
        if (!showSeat.getIsAvailable()) {
            throw new RuntimeException("Seat is already booked");
        }

        // 6️⃣ Lock seat (mark unavailable)
        showSeat.setIsAvailable(false);

        // 7️⃣ Create BookingSeat
        BookingSeat bookingSeat = BookingSeat.builder()
                .booking(booking)
                .seat(seat)
                .build();

        return bookingSeatRepository.save(bookingSeat);
    }

    @Override
    public BookingSeat getBookingSeatById(Long bookingSeatId) {
        return bookingSeatRepository.findById(bookingSeatId)
                .orElseThrow(() -> new RuntimeException("BookingSeat not found"));
    }

    @Override
    public List<BookingSeat> getBookingSeatsByBooking(Long bookingId) {

        // Validate Booking exists
        if (!bookingRepository.existsById(bookingId)) {
            throw new RuntimeException("Booking not found");
        }

        return bookingSeatRepository.findByBooking_BookingId(bookingId);
    }

    @Override
    public void deleteBookingSeat(Long bookingSeatId) {

        BookingSeat bookingSeat = getBookingSeatById(bookingSeatId);

        // Release seat in show_seat
        Long showId = bookingSeat.getBooking().getShow().getShowId();
        Long seatId = bookingSeat.getSeat().getSeatId();

        ShowSeat showSeat = showSeatRepository
                .findByShow_ShowIdAndSeat_SeatId(showId, seatId)
                .orElseThrow(() -> new RuntimeException("ShowSeat not found"));

        showSeat.setIsAvailable(true);

        bookingSeatRepository.delete(bookingSeat);
    }
}
