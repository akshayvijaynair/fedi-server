package com.parimal.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.parimal.blog.entities.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer>{
	
	

}
