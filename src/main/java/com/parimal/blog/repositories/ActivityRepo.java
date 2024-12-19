package com.parimal.blog.repositories;

import com.fasterxml.jackson.databind.JsonNode;
import com.parimal.blog.entities.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface ActivityRepo extends JpaRepository<Activity, Long> {


    @Query("SELECT a FROM Activity a WHERE a.actor IN :actors AND a.type = :type ORDER BY a.published DESC")
    List<Activity> findActivitiesByActorsAndType(Set<String> actors, String type);
}

