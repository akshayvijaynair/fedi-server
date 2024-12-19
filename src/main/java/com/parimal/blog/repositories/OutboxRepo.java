package com.parimal.blog.repositories;

import com.parimal.blog.entities.Outbox;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OutboxRepo extends JpaRepository<Outbox, Long> {
}

