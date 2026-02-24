package com.example.movie_reservation.controller;

import com.example.movie_reservation.model.Screen;
import com.example.movie_reservation.requestDTO.ScreenRequestDTO;
import com.example.movie_reservation.responseDTO.ScreenResponseDTO;
import com.example.movie_reservation.service.ScreenService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/screens")
@RequiredArgsConstructor
public class ScreenController {

    private final ScreenService screenService;

    @PostMapping
    public ScreenResponseDTO createScreens(@RequestBody ScreenRequestDTO screen) {

        return screenService.createScreen(screen);
    }

    @GetMapping("/{id}")
    public ScreenResponseDTO getScreen(@PathVariable Long id) {
        return screenService.getScreenById(id);
    }

    @GetMapping
    public List<ScreenResponseDTO> getAllScreens() {
        return screenService.getAllScreens();
    }

    @GetMapping("/theatre/{theatreId}")
    public List<ScreenResponseDTO> getScreensByTheatre(@PathVariable Long theatreId) {
        return screenService.getScreensByTheatre(theatreId);
    }

    @PutMapping("/{id}")
    public ScreenResponseDTO updateScreen(
            @PathVariable Long id,
            @RequestBody ScreenRequestDTO screen) {
        return screenService.updateScreen(id, screen);
    }

    @DeleteMapping("/{id}")
    public String deleteScreen(@PathVariable Long id) {
        screenService.deleteScreen(id);
        return "Screen deleted successfully";
    }

}
