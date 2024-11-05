package com.parimal.blog.services;

import java.util.List;
import java.util.Map;

import com.parimal.blog.entities.Account;
import com.parimal.blog.payloads.UserDto;

public interface UserService {

	Account registerNewUser(UserDto userDto);

	Account saveAccount(UserDto userDto, Map<String, Object> actorRecord, Map<String, Object> webfingerRecord, String privateKey);
	
	UserDto createUser(UserDto user);
	
	UserDto updateUser(UserDto user, Integer userId);
	
	UserDto getUserById(Integer userId);
	
	List<UserDto> getAllUsers();
	
	void deleteUser(Integer userId);

	// New methods for ActivityPub integration
	Map<String, Object> createActor(String email, String publicKey);

	Map<String, Object> createWebfinger(String email);

	//boolean saveAccount(UserDto user, Map<String, Object> actorRecord, Map<String, Object> webfingerRecord, String privateKey);

}
