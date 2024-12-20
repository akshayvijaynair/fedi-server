package com.parimal.blog.controllers;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.parimal.blog.entities.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.parimal.blog.payloads.ApiResponse;
import com.parimal.blog.payloads.UserDto;
import com.parimal.blog.services.UserService;

@RestController
@RequestMapping(value ="/api/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	//POST-create
	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
		UserDto createUserDto = this.userService.createUser(userDto);
		return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
	}
	
	//PUT-update user
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable("userId") Integer userId){
		UserDto updatedUser = this.userService.updateUser(userDto, userId);
		return ResponseEntity.ok(updatedUser);
	}
	
	//DELETE- delete user
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer uid){
		this.userService.deleteUser(uid);
		return new ResponseEntity<ApiResponse>(new ApiResponse("User deleted Successfully", true), HttpStatus.OK);
	}
	
	//GET - get all user
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getAllUsers(){
		return ResponseEntity.ok(this.userService.getAllUsers());
	}
	
	//GET - get a user
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getSingleUser(@PathVariable Integer userId){
		return ResponseEntity.ok(this.userService.getUserById(userId));
	}

	@GetMapping("/accounts")
	public ResponseEntity<List<Map<String, Object>>> getAllAccountIdsAndNames() {
		  List<Map<String, Object>> accounts = this.userService.getAllAccountIdsAndNames();
		return ResponseEntity.ok(accounts);
	}

	@GetMapping("/search")
	public ResponseEntity<List<Account>> searchAccounts(@RequestParam("query") String query) {
		List<Account> accounts = userService.searchAccounts(query);
		return ResponseEntity.ok(accounts);
	}
}
