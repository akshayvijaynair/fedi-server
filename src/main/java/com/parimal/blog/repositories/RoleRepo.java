package com.parimal.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.parimal.blog.entities.Role;

public interface RoleRepo extends JpaRepository<Role, Integer> {

}
