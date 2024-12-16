package com.parimal.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.parimal.blog.payloads.ApiResponse;
import com.parimal.blog.payloads.CommentDto;
import com.parimal.blog.services.CommentService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	@PostMapping("/post/{postId}/comments")
	public ResponseEntity<CommentDto> createComment(
			@RequestBody CommentDto comment,
			@PathVariable Integer postId
			){
		
		CommentDto commentDto = this.commentService.createComment(comment, postId);
		return new ResponseEntity<CommentDto>(commentDto, HttpStatus.CREATED);
		
	}
	
	@DeleteMapping("/comments/{commentId}")
	public ResponseEntity<ApiResponse> deleteComment(
			@PathVariable Integer commentId
			){
		
		this.commentService.deleteComment(commentId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Comment Deleted Successfully !!", true), HttpStatus.OK);
		
	}

}
