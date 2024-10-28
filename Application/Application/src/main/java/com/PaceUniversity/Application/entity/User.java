package com.PaceUniversity.Application.entity;

import jakarta.persistence.*; // Ensure you're importing JPA correctly
import lombok.Data;

@Data
@Entity
@Table(name = "UserTable")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String bio;
    private String profilePictureURL;

    @Column(nullable = false)
    private boolean active = true;
}
