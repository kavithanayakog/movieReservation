package com.example.movie_reservation.serviceImplementation;

import com.example.movie_reservation.model.Seat;
import com.example.movie_reservation.model.ShowSeat;
import com.example.movie_reservation.model.ShowTime;
import com.example.movie_reservation.repository.SeatRepository;
import com.example.movie_reservation.repository.ShowSeatRepository;
import com.example.movie_reservation.repository.ShowTimeRepository;
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
    public ShowSeat createShowSeat(Long showId, Long seatId) {

        // 1️⃣ Validate Show exists
        ShowTime show = showTimeRepository.findById(showId)
                .orElseThrow(() -> new RuntimeException("Show not found"));

        // 2️⃣ Validate Seat exists
        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(() -> new RuntimeException("Seat not found"));

        // 3️⃣ Validate Seat belongs to the same Screen as Show
        if (!seat.getScreen().getScreenId()
                .equals(show.getScreen().getScreenId())) {
            throw new RuntimeException("Seat does not belong to show's screen");
        }

        // 4️⃣ Prevent duplicate mapping
        boolean exists = showSeatRepository
                .existsByShow_ShowIdAndSeat_SeatId(showId, seatId);

        if (exists) {
            throw new RuntimeException("ShowSeat already exists");
        }

        // 5️⃣ Create ShowSeat
        ShowSeat showSeat = ShowSeat.builder()
                .show(show)
                .seat(seat)
                .isAvailable(true)
                .build();

        return showSeatRepository.save(showSeat);
    }

    @Override
    public ShowSeat getShowSeatById(Long showSeatId) {
        return showSeatRepository.findById(showSeatId)
                .orElseThrow(() -> new RuntimeException("ShowSeat not found"));
    }

    @Override
    public List<ShowSeat> getShowSeatsByShow(Long showId) {

        // Validate show exists
        if (!showTimeRepository.existsById(showId)) {
            throw new RuntimeException("Show not found");
        }

        return showSeatRepository.findByShow_ShowId(showId);
    }

    @Override
    public ShowSeat updateAvailability(Long showSeatId, Boolean isAvailable) {

        ShowSeat showSeat = getShowSeatById(showSeatId);
        showSeat.setIsAvailable(isAvailable);

        return showSeatRepository.save(showSeat);
    }

    @Override
    public void deleteShowSeat(Long showSeatId) {
        ShowSeat showSeat = getShowSeatById(showSeatId);
        showSeatRepository.delete(showSeat);
    }
}

