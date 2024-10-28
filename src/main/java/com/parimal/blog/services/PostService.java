package com.parimal.blog.services;

import java.util.List;

import com.parimal.blog.entities.Post;
import com.parimal.blog.payloads.PostDto;
import com.parimal.blog.payloads.PostResponse;

public interface PostService {

	//create
	PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);
	
	//update
	PostDto updatePost(PostDto postDto, Integer postId);
	
	//delete
	void deletePost(Integer postId);
	
	//get all posts
	PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
	
	//get single post
	PostDto getPostById(Integer postId);
	
	//get all post by category
	PostResponse getPostsByCategory(Integer categoryId, Integer pageNumber, Integer pageSize);
	
	//get post by user
	PostResponse getPostByUser(Integer userId, Integer pageNumber, Integer pageSize);
	
	//Search functionality
	List<PostDto> searchPosts(String keyword);
	
}
