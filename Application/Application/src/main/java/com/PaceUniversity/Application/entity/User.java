package com.PaceUniversity.Application.entity;

import jakarta.persistence.*;
import lombok.Data;

public class User {


   @Data
   @Entity
   @Table(name = "UserTable")



    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

   @Column(nullable = false, unique = true);
   private String username;

    @Column(nullable = false, unique = true);
    private String email;

    @Column(nullable = false);
    private String password;

    private String bio;
    private String profilePictureURL;

    @Column(nullable = false)
    private boolean active = true;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getProfilePictureURL() {
        return profilePictureURL;
    }

    public void setProfilePictureURL(String profilePictureURL) {
        this.profilePictureURL = profilePictureURL;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
