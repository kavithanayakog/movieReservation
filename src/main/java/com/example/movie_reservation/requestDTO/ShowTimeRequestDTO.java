package com.example.movie_reservation.requestDTO;

import com.example.movie_reservation.model.Movie;
import com.example.movie_reservation.model.Screen;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
public class ShowTimeRequestDTO {

    @NotBlank
    private LocalDateTime startTime;

    @NotBlank
    private LocalDateTime endTime;

    @NotBlank
    private LocalDateTime createdDate;

    @NotBlank
    private LocalDateTime updatedDate;

    @NotBlank
    private Long movieId;

    @NotBlank
    private Long screenId;
}
