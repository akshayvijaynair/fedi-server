package com.parimal.blog.payloads;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.parimal.blog.entities.Category;
import com.parimal.blog.entities.Comment;
import com.parimal.blog.entities.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PostDto {

	private Integer id;
	
	private String title;
	
	private String content;
	
	private String imageName;
	
	private Date addedDate;
	
	private CategoryDto category;
	
	private UserDto user;
	
	private Set<CommentDto> comments = new HashSet<>();
	
	
	
}
