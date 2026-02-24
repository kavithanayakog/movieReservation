package com.example.movie_reservation.serviceImplementation;

import com.example.movie_reservation.exception.ResourceNotFoundException;
import com.example.movie_reservation.model.*;
import com.example.movie_reservation.repository.*;
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
    private final BookingSeatRepository bookingSeatRepository;
    private final ShowSeatRepository showSeatRepository;


    @Override
    public SeatResponseDTO createSeat(SeatRequestDTO seat) throws ResourceNotFoundException{
        SeatType seatType = seatTypeRepository.findById(seat.getSeatType())
                .orElseThrow(() -> new ResourceNotFoundException("Seat type not found"));
            Screen screen = screenRepository.findById(seat.getScreen())
                .orElseThrow(() -> new ResourceNotFoundException("Screen not found"));

        boolean exists = seatRepository.existsBySeatNumberAndScreen_ScreenId(
                seat.getSeatNumber(),
                seat.getScreen()
        );
        if (exists) {
            throw new ResourceNotFoundException("Seat already exists for this screen");
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
    public SeatResponseDTO getSeatById(Long seatId) throws ResourceNotFoundException {

        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(() -> new ResourceNotFoundException("Seat not found"));

        return mapToResponse(seat);
    }

    @Override
    public List<SeatResponseDTO> getAllSeats() {

        return seatRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();

    }

    @Override
    public List<SeatResponseDTO> getSeatsByScreen(Long screenId) {

        List<Seat> seat = seatRepository.findAllByScreen_ScreenId(screenId) ;

        if(seat.isEmpty() || !screenRepository.existsById(screenId)) {
            throw new ResourceNotFoundException("No seats found for this screen");
        }

        return seat.stream().map(this::mapToResponse)
                .toList();
    }

    @Override
    public SeatResponseDTO updateSeat(Long seatId, SeatRequestDTO seat) throws ResourceNotFoundException{
        Seat existing = seatRepository.findById(seatId)
                .orElseThrow(() -> new ResourceNotFoundException("Seat not found"));

        SeatType seatType = seatTypeRepository.findById(seat.getSeatType())

                .orElseThrow(() -> new ResourceNotFoundException("seat type not found"));

        Screen screen = screenRepository.findById(seat.getScreen())
                .orElseThrow(() -> new ResourceNotFoundException("Screen not found"));

        existing.setSeatNumber(seat.getSeatNumber());
        existing.setSeatType(seatType);
        existing.setScreen(screen);

        return mapToResponse(seatRepository.save(existing));
    }

    @Override
    public void deleteSeat(Long seatId) throws  ResourceNotFoundException{

        List<BookingSeat> bookings = bookingSeatRepository.findBySeat_SeatId(seatId);

        if(!bookings.isEmpty()){
            throw new ResourceNotFoundException("Cannot delete seat. Active bookings exist.");
        }

        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(() -> new ResourceNotFoundException("Seat not found"));

        //showSeatRepository.deleteBySeat_SeatId(seat.getSeatId());

        //seatRepository.deleteById(seatId);
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
