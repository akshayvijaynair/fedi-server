package com.PaceUniversity.Capstone.service.com.PaceUniversity.Capstone.service;

import com.PaceUniversity.Capstone.entity.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import lombok.NoArgsConstructor;
import lombok.Data;
import lombok.Builder;

import java.util.Collection;
import java.util.Date;
import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_user")
public class AuthenticationService implements UserDetails {

    @Id
    @GeneratedValue

    private Long id;
    private String firstname;
    private String lastname;
    private String username;
    private String email;
    private Date dob;
    private String bio;


    @Enumerated(EnumType.STRING)
    private Role role;



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername(){
        return email;
    }


    @Override
    public String getPassword() {
        return null ;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
