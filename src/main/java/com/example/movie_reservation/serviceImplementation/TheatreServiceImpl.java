package com.example.movie_reservation.serviceImplementation;


import com.example.movie_reservation.model.Theatre;
import com.example.movie_reservation.repository.TheatreRepository;
import com.example.movie_reservation.requestDTO.TheatreRequestDTO;
import com.example.movie_reservation.responseDTO.MovieResponseDTO;
import com.example.movie_reservation.responseDTO.TheatreResponseDTO;
import com.example.movie_reservation.service.TheatreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class TheatreServiceImpl implements TheatreService {

    private final TheatreRepository theatreRepository;

    @Override
    public TheatreResponseDTO createTheatre(TheatreRequestDTO theatre) {

        theatreRepository.findByNameIgnoreCase(theatre.getName())
                .ifPresent(r -> {
                    throw new RuntimeException("Theatre already exists");
                });

        Theatre theatreEntity = Theatre.builder()
                .name(theatre.getName())
                .city(theatre.getCity())
                .state(theatre.getState())
                .location(theatre.getLocation())
                .createdDate(theatre.getCreatedDate())
                .updatedDate(theatre.getUpdatedDate())
                .build();
        return mapToResponse(theatreRepository.save(theatreEntity));
    }

    @Override
    public Theatre getTheatreById(Long theatreId) {
        return theatreRepository.findById(theatreId)
                .orElseThrow(() -> new RuntimeException("Theatre not found"));
    }

    @Override
    public List<Theatre> getAllTheatres() {
        return theatreRepository.findAll();
    }

    @Override
    public TheatreResponseDTO updateTheatre(Long theatreId, TheatreRequestDTO theatre) {
        Theatre existing = getTheatreById(theatreId);

        existing.setName(theatre.getName());
        existing.setCity(theatre.getCity());
        existing.setState(theatre.getState());
        existing.setLocation(theatre.getLocation());

        return mapToResponse(theatreRepository.save(existing));
    }

    @Override
    public void deleteTheatre(Long theatreId) {
        Theatre theatre = getTheatreById(theatreId);
        theatreRepository.delete(theatre);
    }

    private TheatreResponseDTO mapToResponse(Theatre theatre) {

        return new TheatreResponseDTO(
                theatre.getTheatreId(),
                theatre.getName(),
                theatre.getCity(),
                theatre.getState(),
                theatre.getLocation(),
                theatre.getCreatedDate(),
                theatre.getUpdatedDate()
        );
    }
}
