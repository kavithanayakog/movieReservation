package com.example.movie_reservation.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Slf4j
public final class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

     private GlobalExceptionHandler() {}

    @ExceptionHandler(
            value = {
                    UserRoleNotFoundException.class
            })
    public ResponseEntity<String> handleUserRoleNotFoundException(UserRoleNotFoundException ex) {
        log.error("UserRoleNotFoundException: {}", ex.getMessage());
        return ResponseEntity.status(400).body(ex.getMessage());
    }


}
