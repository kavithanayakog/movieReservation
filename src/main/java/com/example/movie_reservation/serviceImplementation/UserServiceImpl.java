package com.example.movie_reservation.serviceImplementation;

import com.example.movie_reservation.exception.ResourceAlreadyExistsException;
import com.example.movie_reservation.exception.ResourceNotFoundException;
import com.example.movie_reservation.model.Role;
import com.example.movie_reservation.model.User;
import com.example.movie_reservation.repository.RoleRepository;
import com.example.movie_reservation.repository.UserRepository;
import com.example.movie_reservation.requestDTO.UserRequestDTO;
import com.example.movie_reservation.responseDTO.UserResponseDTO;
import com.example.movie_reservation.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.movie_reservation.constant.ExceptionMessages.ROLE_NOT_FOUND;
import static com.example.movie_reservation.constant.ExceptionMessages.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    //private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDTO createUser(UserRequestDTO user) {
        System.out.println("Raw password: " + user.getPassword());
        //user.setPassword(encoder.encode(user.getPassword()));
        //return userRepository.save(user);

        if (userRepository.existsByEmailIgnoreCase(user.getEmail())) {
            throw new ResourceAlreadyExistsException(
                    "Email already registered: " + user.getEmail());
        }

        Long roleId = user.getRoleId();
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(ROLE_NOT_FOUND, HttpStatus.NOT_FOUND));


        User userRequest = User.builder()
                .name(user.getName())
                .email(user.getEmail())
                .password(encoder.encode(user.getPassword()))
                .phoneNo(user.getPhoneNo())
                .role(role)
                .build();

        System.out.println("role: " + role);
        System.out.println("Updated userRequest: " + userRequest);
        System.out.println("Updated password: " + userRepository.save(userRequest));
            return mapToResponse(userRepository.save(userRequest));
    }

    @Override
    public UserResponseDTO  getUserById(Long userId) {
        User user =userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return mapToResponse(user);
    }

    @Override
    public List<UserResponseDTO > getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();

     }

    @Override
    public UserResponseDTO updateUser(Long userId, UserRequestDTO user) {

        User userRequest = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                USER_NOT_FOUND, HttpStatus.NOT_FOUND));

        // Email uniqueness check (if changed)
        if (!userRequest.getEmail().equalsIgnoreCase(user.getEmail())
                && userRepository.existsByEmailIgnoreCase(user.getEmail())) {
            throw new ResourceAlreadyExistsException(
                    "Email already registered: " + user.getEmail());
        }

        Role role = roleRepository.findById(user.getRoleId())
                .orElseThrow(() ->
                        new ResourceNotFoundException( ROLE_NOT_FOUND, HttpStatus.NOT_FOUND));

        userRequest.setName(user.getName());
        userRequest.setEmail(user.getEmail());
        userRequest.setPhoneNo(user.getPhoneNo());
        userRequest.setRole(role);

        // Update password only if provided
        if (user.getPassword() != null &&
                !user.getPassword().isBlank()) {
            userRequest.setPassword(encoder.encode(user.getPassword()));
        }
        System.out.println("Updated userRequest: " + userRequest);
        System.out.println("Updated password: " + userRepository.save(userRequest));

        return mapToResponse(userRepository.save(userRequest));

    }

    @Override
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(USER_NOT_FOUND, HttpStatus.NOT_FOUND));

        userRepository.delete(user);
    }

    private UserResponseDTO mapToResponse(User user) {
        return new UserResponseDTO(
                user.getUserId(),
                user.getName(),
                user.getEmail(),
                user.getPhoneNo(),
                user.getRole().getRoleName()
        );
    }
}
