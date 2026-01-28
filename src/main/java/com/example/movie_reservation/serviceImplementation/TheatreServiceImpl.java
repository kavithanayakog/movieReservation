package com.example.movie_reservation.serviceImplementation;


import com.example.movie_reservation.model.Theatre;
import com.example.movie_reservation.repository.TheatreRepository;
import com.example.movie_reservation.service.TheatreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class TheatreServiceImpl implements TheatreService {

    private final TheatreRepository theatreRepository;

    @Override
    public Theatre createTheatre(Theatre theatre) {
        return theatreRepository.save(theatre);
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
    public Theatre updateTheatre(Long theatreId, Theatre theatre) {
        Theatre existing = getTheatreById(theatreId);

        existing.setName(theatre.getName());
        existing.setCity(theatre.getCity());
        existing.setState(theatre.getState());
        existing.setLocation(theatre.getLocation());

        return theatreRepository.save(existing);
    }

    @Override
    public void deleteTheatre(Long theatreId) {
        Theatre theatre = getTheatreById(theatreId);
        theatreRepository.delete(theatre);
    }
}
