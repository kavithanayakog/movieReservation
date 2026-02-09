package com.example.movie_reservation.service;

import com.example.movie_reservation.model.User;
import com.example.movie_reservation.requestDTO.UserRequestDTO;
import com.example.movie_reservation.responseDTO.UserResponseDTO;

import java.util.List;

public interface UserService {

    UserResponseDTO createUser(UserRequestDTO user);

    UserResponseDTO  getUserById(Long userId);

    List<UserResponseDTO > getAllUsers();

    UserResponseDTO  updateUser(Long userId, UserRequestDTO user);

    void deleteUser(Long userId);
}