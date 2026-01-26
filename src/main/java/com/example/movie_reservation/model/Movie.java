package com.example.movie_reservation.model;


import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "movie")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_id")
    private Long movieId;

    @Column(name = "name", nullable = false, length = 150)
    private String name;

    // duration in minutes
    @Column(name = "duration", nullable = false)
    private Integer duration;

    @Column(name = "language", nullable = false, length = 50)
    private String language;
}
