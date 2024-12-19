package com.parimal.blog.services;

import java.util.List;

import com.parimal.blog.payloads.CategoryDto;

public interface CategoryService {
	
	//create
	CategoryDto createCategory(CategoryDto categoryDto);
	
	//update
	CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId);
	
	//delete
	public void deleteCategory(Integer categoryId);
	
	//get
	CategoryDto getcategory(Integer categoryId);
	
	//getAll
	List<CategoryDto> getCategories();

}
