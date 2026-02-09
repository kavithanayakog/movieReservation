package com.example.movie_reservation.serviceImplementation;

import com.example.movie_reservation.model.Movie;
import com.example.movie_reservation.model.Screen;
import com.example.movie_reservation.model.Theatre;
import com.example.movie_reservation.repository.ScreenRepository;
import com.example.movie_reservation.repository.TheatreRepository;
import com.example.movie_reservation.requestDTO.ScreenRequestDTO;
import com.example.movie_reservation.responseDTO.MovieResponseDTO;
import com.example.movie_reservation.responseDTO.ScreenResponseDTO;
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
    public ScreenResponseDTO createScreen(ScreenRequestDTO screen) {

        //  Validating Theatre Exists or not
        Long theatreId = screen.getTheatre_id();
        Theatre theatre = theatreRepository.findById(theatreId)
                .orElseThrow(() -> new RuntimeException("Theatre not found"));

        //  Validating Unique Screen Number per Theatre
        boolean exists = screenRepository.existsByScreenNameAndTheatre_TheatreId(
                screen.getScreenName(), theatreId
        );
        if (exists) {
            throw new RuntimeException("Screen number already exists for this theatre");
        }

        //  Seat Count validation
        if (screen.getTotalSeatCount() <= 0) {
            throw new RuntimeException("Total seat count must be greater than zero");
        }

        Screen screenRequest = Screen.builder()
                .screenName(screen.getScreenName())
                .totalSeatCount(screen.getTotalSeatCount())
                .createdDate(screen.getCreatedDate())
                .updatedDate(screen.getUpdatedDate())
                .theatre(theatre)
                .build();

        return mapToResponse(screenRepository.save(screenRequest));
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
    public ScreenResponseDTO updateScreen(Long screenId, ScreenRequestDTO screen) {

        Screen existing = getScreenById(screenId);

        Long theatreId = screen.getTheatre_id();

        // Checking Theatre Exists or not
        Theatre theatre = theatreRepository.findById(theatreId)
                .orElseThrow(() -> new RuntimeException("Theatre not found"));

        // Validate screen name uniqueness (if changed)
        boolean exists = screenRepository.existsByScreenNameAndTheatre_TheatreId(
                screen.getScreenName(), theatreId
        );

        if (exists && !existing.getScreenName().equals(screen.getScreenName())) {
            throw new RuntimeException("Screen Name already exists for this theatre");
        }

        if (screen.getTotalSeatCount() <= 0) {
            throw new RuntimeException("Total seat count must be greater than zero");
        }

        existing.setScreenName(screen.getScreenName());
        existing.setTotalSeatCount(screen.getTotalSeatCount());
        existing.setCreatedDate(screen.getCreatedDate());
        existing.setUpdatedDate(screen.getUpdatedDate());
        existing.setTheatre(theatre);

        return mapToResponse(screenRepository.save(existing));
    }

    @Override
    public void deleteScreen(Long screenId) {
        Screen screen = getScreenById(screenId);
        screenRepository.delete(screen);
    }

    private ScreenResponseDTO mapToResponse(Screen screen) {
        return new ScreenResponseDTO(
                screen.getScreenId(),
                screen.getScreenName(),
                screen.getTotalSeatCount(),
                screen.getCreatedDate(),
                screen.getUpdatedDate(),
                screen.getTheatre().getName()
        );
    }
}

