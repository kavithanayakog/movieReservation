package com.example.movie_reservation.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@RestControllerAdvice
@Slf4j
public final class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

     private GlobalExceptionHandler() {}

    @ExceptionHandler(
            value = {
                    ResourceNotFoundException.class
            })
    public ResponseEntity<String> handleUserRoleNotFoundException(ResourceNotFoundException ex) {
        log.error("ResourceNotFoundException: {}", ex.getMessage());
        return ResponseEntity.status(400).body(ex.getMessage());
    }

    /*@ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGlobalException(
            Exception ex) {

        log.error("Unhandled Exception: {}", ex.getMessage());
       return ResponseEntity.status(500).body(ex.getMessage());
    }*/
}

