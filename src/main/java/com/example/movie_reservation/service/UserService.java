package com.example.movie_reservation.service;

import com.example.movie_reservation.model.User;

import java.util.List;

public interface UserService {

    User createUser(User user);

    User getUserById(Long userId);

    List<User> getAllUsers();

    User updateUser(Long userId, User user);

    void deleteUser(Long userId);
}