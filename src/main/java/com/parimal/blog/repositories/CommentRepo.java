package com.parimal.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.parimal.blog.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer>{

}
