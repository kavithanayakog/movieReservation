package com.example.movie_reservation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@SpringBootApplication
public class MovieReservationApplication {

    public static void main(String[] args) {

        SpringApplication.run(MovieReservationApplication.class, args);
        System.out.println("Movie Reservation Application is running...");
    }

}
