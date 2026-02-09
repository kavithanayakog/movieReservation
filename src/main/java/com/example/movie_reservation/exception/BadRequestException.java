package com.example.movie_reservation.exception;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {

        super(message);
    }
}
