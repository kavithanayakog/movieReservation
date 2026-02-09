package com.example.movie_reservation.exception;

import lombok.*;
import org.springframework.http.HttpStatus;

@Data
public class CustomException  extends RuntimeException{
    String message;
    HttpStatus httpStatus;

    public CustomException(String message, HttpStatus httpStatus) {
        super(message);
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
