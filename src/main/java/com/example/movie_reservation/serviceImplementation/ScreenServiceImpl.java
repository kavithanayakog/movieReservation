package com.example.movie_reservation.serviceImplementation;

import com.example.movie_reservation.model.Screen;
import com.example.movie_reservation.model.Theatre;
import com.example.movie_reservation.repository.ScreenRepository;
import com.example.movie_reservation.repository.TheatreRepository;
import com.example.movie_reservation.service.ScreenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScreenServiceImpl implements ScreenService {

    private final ScreenRepository screenRepository;
    private final TheatreRepository theatreRepository;

    @Override
    public Screen createScreen(Screen screen) {

        //  Validating Theatre Exists or not
        Long theatreId = screen.getTheatre().getTheatreId();
        Theatre theatre = theatreRepository.findById(theatreId)
                .orElseThrow(() -> new RuntimeException("Theatre not found"));

        //  Validating Unique Screen Number per Theatre
        boolean exists = screenRepository.existsByScreenNumberAndTheatre_TheatreId(
                screen.getScreenNumber(), theatreId
        );
        if (exists) {
            throw new RuntimeException("Screen number already exists for this theatre");
        }

        //  Seat Count validation
        if (screen.getTotalSeatCount() <= 0) {
            throw new RuntimeException("Total seat count must be greater than zero");
        }

        screen.setTheatre(theatre);
        return screenRepository.save(screen);
    }

    @Override
    public Screen getScreenById(Long screenId) {
        return screenRepository.findById(screenId)
                .orElseThrow(() -> new RuntimeException("Screen not found"));
    }

    @Override
    public List<Screen> getAllScreens() {
        return screenRepository.findAll();
    }

    @Override
    public List<Screen> getScreensByTheatre(Long theatreId) {

        // Checking Theatre Exists or not
        if (!theatreRepository.existsById(theatreId)) {
            throw new RuntimeException("Theatre not found");
        }

        return screenRepository.findByTheatre_TheatreId(theatreId);
    }

    @Override
    public Screen updateScreen(Long screenId, Screen screen) {

        Screen existing = getScreenById(screenId);

        Long theatreId = screen.getTheatre().getTheatreId();

        // Checking Theatre Exists or not
        Theatre theatre = theatreRepository.findById(theatreId)
                .orElseThrow(() -> new RuntimeException("Theatre not found"));

        // Validate screen number uniqueness (if changed)
        boolean exists = screenRepository.existsByScreenNumberAndTheatre_TheatreId(
                screen.getScreenNumber(), theatreId
        );

        if (exists && !existing.getScreenNumber().equals(screen.getScreenNumber())) {
            throw new RuntimeException("Screen number already exists for this theatre");
        }

        if (screen.getTotalSeatCount() <= 0) {
            throw new RuntimeException("Total seat count must be greater than zero");
        }

        existing.setScreenNumber(screen.getScreenNumber());
        existing.setTotalSeatCount(screen.getTotalSeatCount());
        existing.setTheatre(theatre);

        return screenRepository.save(existing);
    }

    @Override
    public void deleteScreen(Long screenId) {
        Screen screen = getScreenById(screenId);
        screenRepository.delete(screen);
    }
}

