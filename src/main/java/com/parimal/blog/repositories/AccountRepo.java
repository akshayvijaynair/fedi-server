package com.parimal.blog.repositories;

import com.parimal.blog.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AccountRepo extends JpaRepository<Account, Long> {
    // Additional custom queries can be defined here if needed
    @Query("SELECT a FROM Account a WHERE a.name = :name")
    Optional<Account> findByName(String name);

    @Query("SELECT a FROM Account a WHERE LOWER(a.name) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Account> searchAccountsByName(@Param("query") String query);

    @Query(value = "SELECT actor->>'inbox' FROM accounts WHERE name = :actor", nativeQuery = true)
    String findInboxByActor(String actor);


    // Query by Actor URL (JSONB field)
    @Query(value = "SELECT * FROM accounts WHERE actor->>'id' = :actorUrl", nativeQuery = true)
    Optional<Account> findByActorUrl(String actorUrl);

}
