package com.example.movie_reservation.service;

import com.example.movie_reservation.model.Role;
import com.example.movie_reservation.requestDTO.RoleRequestDTO;
import com.example.movie_reservation.responseDTO.RoleResponseDTO;

import java.util.List;

public interface RoleService {

    RoleResponseDTO createRole(RoleRequestDTO role);

    Role getRoleById(Long roleId);

    List<Role> getAllRoles();

    RoleResponseDTO updateRole(Long roleId, RoleRequestDTO role);

    void deleteRole(Long roleId);
}
