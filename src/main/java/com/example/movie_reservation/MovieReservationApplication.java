package com.example.movie_reservation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MovieReservationApplication {

    public static void main(String[] args) {

        SpringApplication.run(MovieReservationApplication.class, args);
        System.out.println("Movie Reservation Application is running...");
    }

}
