package com.example.movie_reservation.repository;

import com.example.movie_reservation.model.ShowSeat;
import com.example.movie_reservation.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmailIgnoreCase(String email);

    List<User> findByRole_RoleId(Long roleId);
}
