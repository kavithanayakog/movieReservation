package com.example.movie_reservation.serviceImplementation;

import com.example.movie_reservation.exception.ResourceNotFoundException;
import com.example.movie_reservation.model.*;
import com.example.movie_reservation.repository.BookingRepository;
import com.example.movie_reservation.repository.BookingSeatRepository;
import com.example.movie_reservation.repository.SeatRepository;
import com.example.movie_reservation.repository.ShowSeatRepository;
import com.example.movie_reservation.responseDTO.BookingResponseDTO;
import com.example.movie_reservation.responseDTO.BookingSeatResponseDTO;
import com.example.movie_reservation.service.BookingSeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
    public BookingSeatResponseDTO createBookingSeat(Long bookingId, Long seatId) throws ResourceNotFoundException {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(() -> new ResourceNotFoundException("Seat not found"));

        if (bookingSeatRepository
                .existsByBooking_BookingIdAndSeat_SeatId(bookingId, seatId)) {
            throw new ResourceNotFoundException("Seat already added to this booking");
        }

       Long showId = booking.getShow().getShowId();

        ShowSeat showSeat = showSeatRepository
                .findByShow_ShowIdAndSeat_SeatId(showId, seatId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Seat does not belong to this show"));

       if (!showSeat.getIsAvailable()) {
            throw new ResourceNotFoundException("Seat is already booked");
        }

        // making seat as unavailable)
        showSeat.setIsAvailable(false);

        // 7️⃣ Create BookingSeat
        BookingSeat bookingSeat = BookingSeat.builder()
                .booking(booking)
                .seat(seat)
                .build();

        return mapToResponse(bookingSeatRepository.save(bookingSeat));
    }

    @Override
    public BookingSeatResponseDTO getBookingSeatById(Long bookingSeatId) {
        return mapToResponse(bookingSeatRepository.findById(bookingSeatId)
                .orElseThrow(() -> new ResourceNotFoundException("BookingSeat not found")));
    }

    @Override
    public List<BookingSeatResponseDTO> getBookingSeatsByBooking(Long bookingId) {

        if (!bookingRepository.existsById(bookingId)) {
            throw new ResourceNotFoundException("Booking not found");
        }

        return bookingSeatRepository.findByBooking_BookingId(bookingId)
                .stream()
                .map(this::mapToResponse)
                .toList();

    }

    @Override
    public void deleteBookingSeat(Long bookingSeatId) {

        BookingSeat bookingSeat = bookingSeatRepository.findById(bookingSeatId)
                .orElseThrow(() -> new ResourceNotFoundException("BookingSeat not found"));

        // Release seat in show_seat
        Long showId = bookingSeat.getBooking().getShow().getShowId();
        Long seatId = bookingSeat.getSeat().getSeatId();
        Long bookingId = bookingSeat.getBooking().getBookingId();

        ShowSeat showSeat = showSeatRepository
                .findByShow_ShowIdAndSeat_SeatId(showId, seatId)
                .orElseThrow(() -> new ResourceNotFoundException("ShowSeat not found"));

        showSeat.setIsAvailable(true);

        Booking booking = bookingRepository.findById(bookingId).orElseThrow(
                () -> new ResourceNotFoundException("Booking not found"));

        System.out.println("booking.getBookingId() "+booking.getBookingId());
        System.out.println("bookingId "+bookingId);

        bookingRepository.delete(booking);
        System.out.println("deleted  "+bookingId);
        bookingSeatRepository.delete(bookingSeat);
        System.out.println("deleted  seat"+bookingId);
    }

    private BookingSeatResponseDTO mapToResponse(BookingSeat bookingSeat) {
        return new BookingSeatResponseDTO(
                bookingSeat.getBookingSeatId(),
                bookingSeat.getCreatedDate(),
                bookingSeat.getUpdatedDate(),
                bookingSeat.getBooking().getBookingId(),
                bookingSeat.getBooking().getStatus(),
                bookingSeat.getSeat().getSeatNumber(),
                bookingSeat.getSeat().getSeatType().getSeatTypeName()
        );}
}
