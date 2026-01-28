package com.example.movie_reservation.serviceImplementation;

import com.example.movie_reservation.model.Role;
import com.example.movie_reservation.model.User;
import com.example.movie_reservation.service.RoleService;
import com.example.movie_reservation.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role createRole(Role role) {
        roleRepository.findByRoleName(role.getRoleName())
                .ifPresent(r -> {
                    throw new RuntimeException("Role already exists");
                });
        return roleRepository.save(role);
    }

    @Override
    public Role getRoleById(Long roleId) {
        return roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found"));
    }

    @Override
    public List<Role> getAllRoles() {

        return roleRepository.findAll();
    }

    @Override
    public Role updateRole(Long roleId, Role role) {
        Role existingRole = getRoleById(roleId);

        existingRole.setRoleName(role.getRoleName());
        return roleRepository.save(existingRole);
    }

    @Override
    public void deleteRole(Long roleId) {
        Role role = getRoleById(roleId);
        roleRepository.delete(role);
    }


}