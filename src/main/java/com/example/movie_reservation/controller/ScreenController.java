package com.example.movie_reservation.controller;

import com.example.movie_reservation.model.Screen;
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
    public Screen createScreens(@RequestBody Screen screen) {

        return screenService.createScreen(screen);
    }

    @GetMapping("/{id}")
    public Screen getScreen(@PathVariable Long id) {
        return screenService.getScreenById(id);
    }

    @GetMapping
    public List<Screen> getAllScreens() {
        return screenService.getAllScreens();
    }

    @GetMapping("/theatre/{theatreId}")
    public List<Screen> getScreensByTheatre(@PathVariable Long theatreId) {
        return screenService.getScreensByTheatre(theatreId);
    }

    @PutMapping("/{id}")
    public Screen updateScreen(
            @PathVariable Long id,
            @RequestBody Screen screen) {
        return screenService.updateScreen(id, screen);
    }

    @DeleteMapping("/{id}")
    public String deleteScreen(@PathVariable Long id) {
        screenService.deleteScreen(id);
        return "Screen deleted successfully";
    }

}
