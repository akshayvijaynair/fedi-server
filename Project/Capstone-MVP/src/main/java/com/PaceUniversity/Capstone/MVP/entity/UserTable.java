package com.PaceUniversity.Capstone.MVP.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
public class UserTable(name = _"user"){


    @Id
    @Getter
    @Setter
    @NoArgsConstructor
    @GeneratedValue;
    Long id;

    String username;
    String firstname;
    String lastname;
    String password;
    String email;
    LocalDate dateOfBirth;
    String bio;
    Boolean active;





}
