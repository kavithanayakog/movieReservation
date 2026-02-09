package com.example.movie_reservation.responseDTO;

import lombok.*;

@Data
@AllArgsConstructor
public class UserResponseDTO {

    private Long userId;
    private String name;
    private String email;
    private String phoneNo;
    private String roleName;
}
