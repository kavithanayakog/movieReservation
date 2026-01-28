package com.example.movie_reservation.repository;

import com.example.movie_reservation.model.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TheatreRepository extends JpaRepository<Theatre,Long> {

    List<Theatre> findByCityIgnoreCase(String city);
}
