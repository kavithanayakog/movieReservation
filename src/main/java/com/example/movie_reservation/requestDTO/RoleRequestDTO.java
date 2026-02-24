package com.example.movie_reservation.requestDTO;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
public class RoleRequestDTO {

    //@NotBlank(message = "Role Name cannot be empty")
    @NotBlank
    private String roleName;
}
