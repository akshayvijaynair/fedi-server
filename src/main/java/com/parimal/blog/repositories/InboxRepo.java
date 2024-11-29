package com.parimal.blog.repositories;

import com.parimal.blog.entities.Inbox;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InboxRepo extends JpaRepository<Inbox, Long> {
}

