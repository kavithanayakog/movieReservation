package com.example.movie_reservation.serviceImplementation;


import com.example.movie_reservation.exception.ResourceNotFoundException;
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
    private final PaymentRepository paymentRepository;

    @Override
    public BookingResponseDTO createBooking(Long userId, Long showId, List<Long> seatIds) throws  ResourceNotFoundException{

       User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

       ShowTime show = showTimeRepository.findById(showId)
                .orElseThrow(() -> new ResourceNotFoundException("Show not found"));

        System.out.println(" showId"+showId );
        System.out.println(" seatIds"+seatIds );

        //  Validate Seats Exist for Show
        List<ShowSeat> showSeats =
                showSeatRepository.findByShow_ShowIdAndSeat_SeatIdIn(showId, seatIds);

       // System.out.println(" showSeats"+showSeats );
        //System.out.println(" seatIds.size()"+showSeats.size() );
        //System.out.println(" seatIds.size()"+ seatIds.size());
        if (showSeats.size() != seatIds.size()) {
            throw new ResourceNotFoundException("Invalid seats selected");
        }

        // Validate Seat Availability
        for (ShowSeat ss : showSeats) {
            if (!ss.getIsAvailable()) {
                throw new ResourceNotFoundException("One or more seats already booked");
            }
        }

        // Calculate Amount
        BigDecimal totalAmount = showSeats.stream()
                .map(ss -> ss.getSeat().getSeatType().getAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);


        // Create Booking
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
    public BookingResponseDTO getBookingById(Long bookingId) throws ResourceNotFoundException{
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with booking id" + bookingId));

        return mapToDTO(booking);
    }

    @Override
    public List<BookingResponseDTO> getAllBookings() {

        return bookingRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public BookingResponseDTO cancelBooking(Long bookingId) throws ResourceNotFoundException {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found" + bookingId));

        if ("CANCELLED".equals(booking.getStatus())) {
            throw new ResourceNotFoundException("Booking already cancelled");
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
    public void deleteBooking(Long bookingId) throws ResourceNotFoundException{

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"+bookingId));

        if (!booking.getStatus().equals("CANCELLED")) {
            throw new ResourceNotFoundException("Only cancelled bookings can be deleted");
        }

        bookingSeatRepository.deleteByBooking_BookingId(bookingId);
        paymentRepository.deleteByBooking_BookingId(bookingId);
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
