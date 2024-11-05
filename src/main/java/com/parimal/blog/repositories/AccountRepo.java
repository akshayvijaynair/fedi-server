package com.parimal.blog.repositories;

import com.parimal.blog.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepo extends JpaRepository<Account, Long> {
    // Additional custom queries can be defined here if needed
    Optional<Account> findByName(String name);
}
