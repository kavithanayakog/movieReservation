package com.example.movie_reservation.serviceImplementation;


import com.example.movie_reservation.exception.ResourceNotFoundException;
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
    public RoleResponseDTO createRole(RoleRequestDTO roleRequest) throws ResourceNotFoundException {

        roleRepository.findByRoleName(roleRequest.getRoleName())
                .ifPresent(r -> {
                    throw new ResourceNotFoundException("Role Name already exists" + roleRequest.getRoleName());
                });

        Role roleEntity = Role.builder()
                .roleName(roleRequest.getRoleName())
                .build();

        return mapToEntity(roleRepository.save(roleEntity));
    }

    @Override
    public Role getRoleById(Long roleId) throws ResourceNotFoundException {
        return roleRepository.findById(roleId)
                .orElseThrow(() ->  new ResourceNotFoundException("Role ID not found " + roleId));
    }

    @Override
    public List<Role> getAllRoles() {

        return roleRepository.findAll();
    }

    @Override
    public RoleResponseDTO updateRole(Long roleId, RoleRequestDTO role) throws ResourceNotFoundException{
        Role existingRole = getRoleById(roleId);

        roleRepository.findByRoleName(role.getRoleName())
                .ifPresent(r -> {
                    throw new ResourceNotFoundException(" Role Name already exists " + role.getRoleName());
                });

        existingRole.setRoleName(role.getRoleName());


        return mapToEntity(roleRepository.save(existingRole));
    }

    @Override
    public void deleteRole(Long roleId) throws ResourceNotFoundException {

        Role role = getRoleById(roleId);
        //usersRepository.deleteAll(
                //usersRepository.findByRole_RoleId(roleId));

        usersRepository.findByRole_RoleId(roleId)
                .stream()
                .findFirst()
                .ifPresent(u -> {
                    throw new ResourceNotFoundException(
                            "Cannot delete role with assigned users " + roleId
                    );
                });
        roleRepository.delete(role);
    }

    private RoleResponseDTO mapToEntity(Role role) {

        return new RoleResponseDTO(
                role.getRoleId(),
                role.getRoleName()
        );
    }

}