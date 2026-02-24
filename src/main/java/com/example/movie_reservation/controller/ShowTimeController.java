package com.example.movie_reservation.controller;


import com.example.movie_reservation.model.ShowTime;
import com.example.movie_reservation.requestDTO.ShowTimeRequestDTO;
import com.example.movie_reservation.responseDTO.ShowTimeResponseDTO;
import com.example.movie_reservation.service.ShowTimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/show-times")
public class ShowTimeController {

    private final ShowTimeService showTimeService;

    @PostMapping
    public ShowTimeResponseDTO createShowTime(@RequestBody ShowTimeRequestDTO showTime) {

        return showTimeService.createShowTime(showTime);
    }

    @GetMapping
    public List<ShowTimeResponseDTO> getAllShowTime() {
        return showTimeService.getAllShowTime();
    }
    @GetMapping("/{id}")
    public ShowTimeResponseDTO getShowTimeById(@PathVariable Long id) {

        return showTimeService.getShowTimeById(id);
    }

    @GetMapping("/movie/{movieId}")
    public List<ShowTimeResponseDTO> getShowsByMovie(@PathVariable Long movieId) {
        return showTimeService.getShowsByMovie(movieId);
    }

    @PutMapping("/{id}")
    public ShowTimeResponseDTO updateShowTime(
            @PathVariable Long id,
            @RequestBody ShowTimeRequestDTO showTime) {
        return showTimeService.updateShowTime(id, showTime);
    }

    @DeleteMapping("/{id}")
    public String deleteShowTime(@PathVariable Long id) {
        showTimeService.deleteShowTime(id);
        return "ShowTime deleted successfully";
    }
}
