package com.example.movie_reservation.requestDTO;


import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
public class RoleRequestDTO {

    @NotBlank
    private String roleName;
}
