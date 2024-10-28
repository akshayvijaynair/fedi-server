package com.parimal.blog.payloads;

import com.parimal.blog.entities.Post;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto {
	
    private int id;
	
	private String content;
	
	
}