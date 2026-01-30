package com.example.movie_reservation.serviceImplementation;

import com.example.movie_reservation.model.Seat;
import com.example.movie_reservation.repository.SeatRepository;
import com.example.movie_reservation.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatServiceImpl implements SeatService {

    private final SeatRepository seatRepository;

    @Override
    public Seat createSeat(Seat seat) {
        boolean exists = seatRepository.existsBySeatNumberAndScreen_ScreenId(
                seat.getSeatNumber(),
                seat.getScreen().getScreenId()
        );
        if (exists) {
            throw new RuntimeException("Seat already exists for this screen");
        }
        return seatRepository.save(seat);
    }

    @Override
    public Seat getSeatById(Long seatId) {
        return seatRepository.findById(seatId)
                .orElseThrow(() -> new RuntimeException("Seat not found"));
    }

    @Override
    public List<Seat> getAllSeats() {
        return seatRepository.findAll();
    }

    @Override
    public List<Seat> getSeatsByScreen(Long screenId) {
        return seatRepository.findByScreen_ScreenId(screenId);
    }

    @Override
    public Seat updateSeat(Long seatId, Seat seat) {
        Seat existing = getSeatById(seatId);

        existing.setSeatNumber(seat.getSeatNumber());
        existing.setSeatType(seat.getSeatType());
        existing.setScreen(seat.getScreen());

        return seatRepository.save(existing);
    }

    @Override
    public void deleteSeat(Long seatId) {
        Seat seat = getSeatById(seatId);
        seatRepository.delete(seat);
    }
}
