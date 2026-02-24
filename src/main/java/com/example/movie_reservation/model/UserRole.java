package com.example.movie_reservation.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name ="user_role")
public class UserRole {

  @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="user_role_id")
    private Long userRoleId;

    @Column(name = "user_id", nullable = false, unique = true, length = 50)
    private Long user_id;

    @Column(name = "role_id", nullable = false, unique = true, length = 50)
    private Long role_id;

}
