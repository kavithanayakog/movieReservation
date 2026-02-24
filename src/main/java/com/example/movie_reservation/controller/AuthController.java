package com.example.movie_reservation.controller;

import com.example.movie_reservation.JwtUtil;
import com.example.movie_reservation.exception.ResourceNotFoundException;
import com.example.movie_reservation.model.User;
import com.example.movie_reservation.repository.UserRepository;
import com.example.movie_reservation.requestDTO.LoginRequestDTO;
import com.example.movie_reservation.responseDTO.AuthResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepo;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

   /* @PostMapping("/login")
    public String login(@RequestBody LoginRequestDTO login) {
        User dbUser = userRepo.findByEmail(login.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(login.getPassword(), dbUser.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }
        return jwtUtil.generateToken(dbUser.getEmail());
    } */



    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequestDTO login)  throws ResourceNotFoundException{

        User dbUser = userRepo.findByEmail(login.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!passwordEncoder.matches(login.getPassword(), dbUser.getPassword())) {
            throw new ResourceNotFoundException("Invalid password");
        }

        // Generate JWT token
        String token = jwtUtil.generateToken(dbUser.getEmail());

        AuthResponse response = new AuthResponse(
                token,
                dbUser.getUserId(),
                dbUser.getEmail(),
                dbUser.getRole().getRoleName()
        );

        return ResponseEntity.ok(response);
    }
}


