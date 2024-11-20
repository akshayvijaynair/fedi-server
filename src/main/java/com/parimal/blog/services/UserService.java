package com.parimal.blog.services;

import java.util.List;
import java.util.Map;

import com.parimal.blog.entities.Account;
import com.parimal.blog.payloads.UserDto;

public interface UserService {

	/**
	 * Registers a new user and creates an associated ActivityPub account.
	 * @param userDto The user data transfer object containing user information.
	 * @return The created Account entity with ActivityPub data.
	 */
	Account registerNewUser(UserDto userDto);

	/**
	 * Saves an Account with ActivityPub information.
	 * @param userDto The user data transfer object containing user information.
	 * @param actorRecord A map containing ActivityPub actor data.
	 * @param webfingerRecord A map containing Webfinger data.
	 * @param privateKey The private key for the user.
	 * @return The saved Account entity.
	 */
	Account saveAccount(UserDto userDto, Map<String, Object> actorRecord, Map<String, Object> webfingerRecord, String privateKey);

	/**
	 * Creates a new user based on provided user data.
	 * @param user The UserDto containing the new user's information.
	 * @return The created UserDto.
	 */
	UserDto createUser(UserDto user);

	/**
	 * Updates an existing user's information.
	 * @param user The UserDto containing updated user information.
	 * @param userId The ID of the user to update.
	 * @return The updated UserDto.
	 */
	UserDto updateUser(UserDto user, Integer userId);

	/**
	 * Retrieves a user's information by their ID.
	 * @param userId The ID of the user.
	 * @return The retrieved UserDto.
	 */
	UserDto getUserById(Integer userId);

	/**
	 * Retrieves a list of all users.
	 * @return A list of UserDto objects.
	 */
	List<UserDto> getAllUsers();

	/**
	 * Deletes a user by their ID.
	 * @param userId The ID of the user to delete.
	 */
	void deleteUser(Integer userId);

	/**
	 * Creates an ActivityPub Actor representation for a user.
	 * @param email The email of the user.
	 * @param publicKey The public key for the user.
	 * @return A map containing the ActivityPub actor data.
	 */
	Map<String, Object> createActor(String email, String publicKey);

	/**
	 * Creates a Webfinger record for a user.
	 * @param email The email of the user.
	 * @return A map containing the Webfinger data.
	 */
	Map<String, Object> createWebfinger(String email);

	/**
	 * Retrieves all accounts with only their IDs and names.
	 * @return A list of maps containing account IDs and names.
	 */
	List<Map<String, Object>> getAllAccountIdsAndNames();


}
