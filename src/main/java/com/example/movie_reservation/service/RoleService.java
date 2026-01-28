package com.example.movie_reservation.service;

import com.example.movie_reservation.model.Role;

import java.util.List;

public interface RoleService {

    Role createRole(Role role);

    Role getRoleById(Long roleId);

    List<Role> getAllRoles();

    Role updateRole(Long roleId, Role role);

    void deleteRole(Long roleId);
}
