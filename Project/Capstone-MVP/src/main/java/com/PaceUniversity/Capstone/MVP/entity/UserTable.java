package com.PaceUniversity.Capstone.MVP.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table
public class UserTable(){


    @Id

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
