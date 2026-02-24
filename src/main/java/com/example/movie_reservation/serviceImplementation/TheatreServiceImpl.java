package com.example.movie_reservation.serviceImplementation;


import com.example.movie_reservation.exception.ResourceNotFoundException;
import com.example.movie_reservation.model.Theatre;
import com.example.movie_reservation.repository.TheatreRepository;
import com.example.movie_reservation.requestDTO.TheatreRequestDTO;
import com.example.movie_reservation.responseDTO.MovieResponseDTO;
import com.example.movie_reservation.responseDTO.TheatreResponseDTO;
import com.example.movie_reservation.service.TheatreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor

public class TheatreServiceImpl implements TheatreService {

    private final TheatreRepository theatreRepository;

    @Override
    public TheatreResponseDTO createTheatre(TheatreRequestDTO theatre) throws ResourceNotFoundException {

        theatreRepository.findByNameIgnoreCase(theatre.getName())
                .ifPresent(r -> {
                    throw new ResourceNotFoundException("Theatre already exists");
                });

        if(theatre.getName().isEmpty() || "null".equalsIgnoreCase(theatre.getName())){

            throw new ResourceNotFoundException("Name can not be empty or null");
        }
        if(theatre.getCity().isEmpty() || "null".equalsIgnoreCase(theatre.getCity())){

            throw new ResourceNotFoundException("City can not be empty or null");
        }

        if(theatre.getState().isEmpty() || "null".equalsIgnoreCase(theatre.getState())){

            throw new ResourceNotFoundException("State can not be empty or null");
        }
        if(theatre.getLocation().isEmpty() || "null".equalsIgnoreCase(theatre.getLocation())){

            throw new ResourceNotFoundException("Location can not be empty or null");
        }

        Theatre theatreEntity = Theatre.builder()
                .name(theatre.getName())
                .city(theatre.getCity())
                .state(theatre.getState())
                .location(theatre.getLocation())
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();
        return mapToResponse(theatreRepository.save(theatreEntity));
    }

    @Override
    public Theatre getTheatreById(Long theatreId) throws ResourceNotFoundException{
        return theatreRepository.findById(theatreId)
                .orElseThrow(() -> new ResourceNotFoundException("Theatre not found"));
    }

    @Override
    public List<Theatre> getAllTheatres() {
        return theatreRepository.findAll();
    }

    @Override
    public TheatreResponseDTO updateTheatre(Long theatreId, TheatreRequestDTO theatre) {
        Theatre existing = getTheatreById(theatreId);

        if(theatre.getName().isEmpty() || "null".equalsIgnoreCase(theatre.getName())){

            throw new ResourceNotFoundException("Name can not be empty or null");
        }
        if(theatre.getCity().isEmpty() || "null".equalsIgnoreCase(theatre.getCity())){

            throw new ResourceNotFoundException("City can not be empty or null");
        }

        if(theatre.getState().isEmpty() || "null".equalsIgnoreCase(theatre.getState())){

            throw new ResourceNotFoundException("State can not be empty or null");
        }
        if(theatre.getLocation().isEmpty() || "null".equalsIgnoreCase(theatre.getLocation())){

            throw new ResourceNotFoundException("Location can not be empty or null");
        }

        existing.setName(theatre.getName());
        existing.setCity(theatre.getCity());
        existing.setState(theatre.getState());
        existing.setLocation(theatre.getLocation());
        existing.setUpdatedDate(LocalDateTime.now());

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
