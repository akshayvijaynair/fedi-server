package com.PaceUniversity.Application.repository;


import com.PaceUniversity.Application.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findBuserNAme(String username);
    Optional<User> findByemail(String email);



}
