package com.example.movie_reservation.serviceImplementation;


import com.example.movie_reservation.exception.UserRoleNotFoundException;
import com.example.movie_reservation.model.Role;
import com.example.movie_reservation.repository.RoleRepository;
import com.example.movie_reservation.repository.UserRepository;
import com.example.movie_reservation.requestDTO.RoleRequestDTO;
import com.example.movie_reservation.responseDTO.RoleResponseDTO;
import com.example.movie_reservation.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final UserRepository usersRepository;

    @Override
    public RoleResponseDTO createRole(RoleRequestDTO roleRequest) {

        roleRepository.findByRoleName(roleRequest.getRoleName())
                .ifPresent(r -> {
                    throw new RuntimeException("Role already exists");
                });

        Role roleEntity = Role.builder()
                .roleName(roleRequest.getRoleName())
                .build();

        return mapToEntity(roleRepository.save(roleEntity));
    }

    @Override
    public Role getRoleById(Long roleId) throws UserRoleNotFoundException {
        return roleRepository.findById(roleId)
                .orElseThrow(() ->  new UserRoleNotFoundException("Role not found"));
    }

    @Override
    public List<Role> getAllRoles() {

        return roleRepository.findAll();
    }

    @Override
    public RoleResponseDTO updateRole(Long roleId, RoleRequestDTO role) throws UserRoleNotFoundException{
        Role existingRole = getRoleById(roleId);

        existingRole.setRoleName(role.getRoleName());
        return mapToEntity(roleRepository.save(existingRole));
    }

    @Override
    public void deleteRole(Long roleId) throws UserRoleNotFoundException {

        Role role = getRoleById(roleId);
        usersRepository.deleteAll(
                usersRepository.findByRole_RoleId(roleId));
        roleRepository.delete(role);
    }

    private RoleResponseDTO mapToEntity(Role role) {

        return new RoleResponseDTO(
                role.getRoleId(),
                role.getRoleName()
        );
    }

}