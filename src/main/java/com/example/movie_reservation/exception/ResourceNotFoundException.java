package com.example.movie_reservation.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends CustomException{
    public ResourceNotFoundException(String message, HttpStatus httpStatus) {

        super(message, httpStatus);
    }
}
