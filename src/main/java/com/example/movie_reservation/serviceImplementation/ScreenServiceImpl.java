package com.example.movie_reservation.serviceImplementation;

import com.example.movie_reservation.exception.ResourceNotFoundException;
import com.example.movie_reservation.model.Movie;
import com.example.movie_reservation.model.Role;
import com.example.movie_reservation.model.Screen;
import com.example.movie_reservation.model.Theatre;
import com.example.movie_reservation.repository.ScreenRepository;
import com.example.movie_reservation.repository.SeatRepository;
import com.example.movie_reservation.repository.TheatreRepository;
import com.example.movie_reservation.requestDTO.ScreenRequestDTO;
import com.example.movie_reservation.responseDTO.MovieResponseDTO;
import com.example.movie_reservation.responseDTO.ScreenResponseDTO;
import com.example.movie_reservation.service.ScreenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScreenServiceImpl implements ScreenService {

    private final ScreenRepository screenRepository;
    private final TheatreRepository theatreRepository;
    private final SeatRepository seatRepository;

    @Override
    public ScreenResponseDTO createScreen(ScreenRequestDTO screen) throws ResourceNotFoundException {

        //  Validating Theatre Exists or not
        Long theatreId = screen.getTheatre_id();
        Theatre theatre = theatreRepository.findById(theatreId)
                .orElseThrow(() -> new ResourceNotFoundException("Theatre not found"));

        //  Validating Unique Screen Number per Theatre
        boolean exists = screenRepository.existsByScreenNameAndTheatre_TheatreId(
                screen.getScreenName(), theatreId);

        if (exists) {
            throw new ResourceNotFoundException("Screen name already exists for this theatre");
        }

        //  Seat Count validation
        if (screen.getTotalSeatCount() <= 0) {
            throw new ResourceNotFoundException("Total seat count must be greater than zero");
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
    public ScreenResponseDTO getScreenById(Long screenId) {
        Screen screen = screenRepository.findById(screenId)
                .orElseThrow(() -> new ResourceNotFoundException("Screen not found"));

        return  mapToResponse(screen);
    }

    @Override
    public List<ScreenResponseDTO> getAllScreens() {

        List<Screen> screens = screenRepository.findAll();

        return screens.stream()
                .map(this::mapToResponse)
                .toList();

    }

    @Override
    public List<ScreenResponseDTO> getScreensByTheatre(Long theatreId) {

        // Checking Theatre Exists or not
        if (!theatreRepository.existsById(theatreId)) {
            throw new ResourceNotFoundException("Theatre not found");
        }

        //screenRepository.findById(theatreId)
               // .orElseThrow(() ->
                   //     new ResourceNotFoundException("Theatre not found: " + theatreId));

        List<Screen> screens = screenRepository.findByTheatre_TheatreId(theatreId);

        if (screens.isEmpty()) {
            throw new ResourceNotFoundException("No screens found for theatre: " + theatreId);
        }


        return screens.stream()
                .map(this::mapToResponse)
                .toList();


    }

    @Override
    public ScreenResponseDTO updateScreen(Long screenId, ScreenRequestDTO screen) {

        Screen existing = screenRepository.findById(screenId)
                .orElseThrow(() -> new ResourceNotFoundException("Screen not found"));

        Long theatreId = screen.getTheatre_id();

        // Checking Theatre Exists or not
        Theatre theatre = theatreRepository.findById(theatreId)
                .orElseThrow(() -> new ResourceNotFoundException("Theatre not found"));

        // Validate screen name uniqueness (if changed)
        boolean exists = screenRepository.existsByScreenNameAndTheatre_TheatreId(
                screen.getScreenName(), theatreId
        );

        if (exists && !existing.getScreenName().equals(screen.getScreenName())) {
            throw new ResourceNotFoundException("Screen Name already exists for this theatre");
        }

        if (screen.getTotalSeatCount() <= 0) {
            throw new ResourceNotFoundException("Total seat count must be greater than zero");
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
        Screen screen = screenRepository.findById(screenId)
                .orElseThrow(() -> new ResourceNotFoundException("Screen not found"));

        if (seatRepository.existsByScreen_ScreenId(screenId)) {
            throw new ResourceNotFoundException(
                    "Cannot delete screen with assigned seats: " + screenId);
        }

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

