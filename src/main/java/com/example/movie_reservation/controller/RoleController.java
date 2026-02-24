package com.example.movie_reservation.controller;


import com.example.movie_reservation.model.Role;
import com.example.movie_reservation.requestDTO.RoleRequestDTO;
import com.example.movie_reservation.responseDTO.RoleResponseDTO;
import com.example.movie_reservation.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {

   private final RoleService roleService;

    @PostMapping
    public RoleResponseDTO createRole(@RequestBody RoleRequestDTO role) {

        System.out.println("Creating role: " + role);
        return roleService.createRole(role);
    }

    @GetMapping("/{id}")
    public Role getRole(@PathVariable Long id) {
        return roleService.getRoleById(id);
    }

    @GetMapping
    public List<Role> getAllRoles() {
        return roleService.getAllRoles();
    }

    @PutMapping("/{id}")
    public  RoleResponseDTO updateRole(
            @PathVariable Long id,
            @RequestBody RoleRequestDTO role) {

        return roleService.updateRole(id, role);
    }

    @DeleteMapping("/{id}")
    public String deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return "Role deleted successfully";
    }
}
