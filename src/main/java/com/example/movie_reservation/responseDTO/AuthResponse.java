package com.example.movie_reservation.responseDTO;


import lombok.*;

@Data
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private Long userId;
    private String email;
    private String role;
}
