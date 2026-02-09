package com.example.movie_reservation.serviceImplementation;

import com.example.movie_reservation.model.Movie;
import com.example.movie_reservation.model.Screen;
import com.example.movie_reservation.model.Seat;
import com.example.movie_reservation.model.SeatType;
import com.example.movie_reservation.repository.ScreenRepository;
import com.example.movie_reservation.repository.SeatRepository;
import com.example.movie_reservation.repository.SeatTypeRepository;
import com.example.movie_reservation.requestDTO.SeatRequestDTO;
import com.example.movie_reservation.responseDTO.SeatResponseDTO;
import com.example.movie_reservation.responseDTO.UserResponseDTO;
import com.example.movie_reservation.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatServiceImpl implements SeatService {

    private final SeatRepository seatRepository;
    private final SeatTypeRepository seatTypeRepository;
    private final ScreenRepository screenRepository;

    @Override
    public SeatResponseDTO createSeat(SeatRequestDTO seat) {
        SeatType seatType = seatTypeRepository.findById(seat.getSeatType())
                .orElseThrow(() -> new RuntimeException("Seat type not found"));
            Screen screen = screenRepository.findById(seat.getScreen())
                .orElseThrow(() -> new RuntimeException("Screen not found"));

        boolean exists = seatRepository.existsBySeatNumberAndScreen_ScreenId(
                seat.getSeatNumber(),
                seat.getScreen()
        );
        if (exists) {
            throw new RuntimeException("Seat already exists for this screen");
        }

        Seat seatRequest = Seat.builder()
                .seatNumber(seat.getSeatNumber())
                .createdDate(seat.getCreatedDate())
                .updatedDate(seat.getUpdatedDate())
                .seatType(seatType)
                .screen(screen)
                .build();

        return mapToResponse(seatRepository.save(seatRequest));
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
    public SeatResponseDTO updateSeat(Long seatId, SeatRequestDTO seat) {
        Seat existing = getSeatById(seatId);

        SeatType seatType = seatTypeRepository.findById(seat.getSeatType())

                .orElseThrow(() -> new RuntimeException("seat type not found"));

        Screen screen = screenRepository.findById(seat.getScreen())
                .orElseThrow(() -> new RuntimeException("Screen not found"));

        existing.setSeatNumber(seat.getSeatNumber());
        existing.setSeatType(seatType);
        existing.setScreen(screen);

        return mapToResponse(seatRepository.save(existing));
    }

    @Override
    public void deleteSeat(Long seatId) {
        Seat seat = getSeatById(seatId);
        seatRepository.delete(seat);
    }

    private SeatResponseDTO mapToResponse(Seat seat) {

        return new SeatResponseDTO(
                seat.getSeatId(),
                seat.getSeatNumber(),
                seat.getCreatedDate(),
                seat.getUpdatedDate(),
                seat.getSeatType().getSeatTypeName(),
                seat.getScreen().getScreenName()
        );
    }
}
