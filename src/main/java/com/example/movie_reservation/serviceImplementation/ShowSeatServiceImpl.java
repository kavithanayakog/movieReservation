package com.example.movie_reservation.serviceImplementation;

import com.example.movie_reservation.exception.ResourceNotFoundException;
import com.example.movie_reservation.model.Seat;
import com.example.movie_reservation.model.ShowSeat;
import com.example.movie_reservation.model.ShowTime;
import com.example.movie_reservation.repository.SeatRepository;
import com.example.movie_reservation.repository.ShowSeatRepository;
import com.example.movie_reservation.repository.ShowTimeRepository;
import com.example.movie_reservation.responseDTO.ShowSeatResponseDTO;
import com.example.movie_reservation.service.ShowSeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ShowSeatServiceImpl implements ShowSeatService {

    private final ShowSeatRepository showSeatRepository;
    private final ShowTimeRepository showTimeRepository;
    private final SeatRepository seatRepository;

    @Override
    public ShowSeatResponseDTO createShowSeat(Long showId, Long seatId) throws ResourceNotFoundException {

        // 1️⃣ Validate Show exists
        ShowTime show = showTimeRepository.findById(showId)
                .orElseThrow(() -> new ResourceNotFoundException("Show not found"));

        // 2️⃣ Validate Seat exists
        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(() -> new ResourceNotFoundException("Seat not found"));

        // 3️⃣ Validate Seat belongs to the same Screen as Show
        if (!seat.getScreen().getScreenId()
                .equals(show.getScreen().getScreenId())) {
            throw new ResourceNotFoundException("Seat does not belong to show's screen");
        }

        // 4️⃣ Prevent duplicate mapping
        boolean exists = showSeatRepository
                .existsByShow_ShowIdAndSeat_SeatId(showId, seatId);

        if (exists) {
            throw new ResourceNotFoundException("ShowSeat already exists");
        }

        // 5️⃣ Create ShowSeat
        ShowSeat showSeat = ShowSeat.builder()
                .show(show)
                .seat(seat)
                .isAvailable(true)
                .build();

        return mapToDTO(showSeatRepository.save(showSeat));
    }

    @Override
    public ShowSeatResponseDTO getShowSeatById(Long showSeatId)  throws ResourceNotFoundException{
        return mapToDTO(showSeatRepository.findById(showSeatId)
                .orElseThrow(() -> new ResourceNotFoundException("ShowSeat not found")));
    }

    @Override
    public List<ShowSeatResponseDTO> getShowSeatsByShow(Long showId)  throws ResourceNotFoundException{

        System.out.println( " showId  "+showId);
        // Validate show exists
        if (!showTimeRepository.existsById(showId)) {
            throw new ResourceNotFoundException("Show not found");
        }

        return showSeatRepository.findByShow_ShowId(showId)
                .stream()
                .map(this::mapToDTO)
                .toList();


            // return mapToDTO(showSeatRepository.findByShow_ShowId(showId));
    }

    @Override
    public ShowSeatResponseDTO updateAvailability(Long showSeatId, Boolean isAvailable) {

        ShowSeat showSeat = showSeatRepository.findById(showSeatId)
                .orElseThrow(() -> new ResourceNotFoundException("ShowSeat not found"));
        showSeat.setIsAvailable(isAvailable);

        return mapToDTO(showSeatRepository.save(showSeat));
    }

    @Override
    public void deleteShowSeat(Long showSeatId) {
        ShowSeat showSeat = showSeatRepository.findById(showSeatId)
                .orElseThrow(() -> new ResourceNotFoundException("ShowSeat not found"));
        showSeatRepository.delete(showSeat);
    }

    private ShowSeatResponseDTO mapToDTO(ShowSeat showSeat) {
        return new ShowSeatResponseDTO(
                showSeat.getShowSeatId(),
                showSeat.getIsAvailable(),
                showSeat.getCreatedDate(),
                showSeat.getUpdatedDate(),
                showSeat.getShow().getShowId(),
                showSeat.getSeat().getSeatNumber());
    }
}

