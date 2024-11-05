package com.parimal.blog.services.impl;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parimal.blog.entities.Account;
import com.parimal.blog.repositories.AccountRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.parimal.blog.exceptions.*;
import com.parimal.blog.config.AppConstants;
import com.parimal.blog.entities.Role;
import com.parimal.blog.entities.User;
import com.parimal.blog.payloads.UserDto;
import com.parimal.blog.repositories.RoleRepo;
import com.parimal.blog.repositories.UserRepo;
import com.parimal.blog.services.UserService;

import static io.jsonwebtoken.impl.crypto.EllipticCurveProvider.generateKeyPair;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepo userRepo;

	@Autowired
	private AccountRepo accountRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private ObjectMapper objectMapper; // Jackson ObjectMapper for JSON serialization
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepo roleRepo;

	@Override
	public UserDto createUser(UserDto userDto) {
	
		User user = this.dtoToUser(userDto);
		User savedUser = this.userRepo.save(user);
		return this.userToDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		
		User user=this.userRepo.findById(userId)
				.orElseThrow( () -> new ResourceNotFoundException("User","id",userId));
		
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());
		
		User updatedUser=this.userRepo.save(user);
		UserDto userDto1 = this.userToDto(updatedUser);
		return userDto1;
	}

	@Override
	public UserDto getUserById(Integer userId) {
		User user=this.userRepo.findById(userId)
				.orElseThrow( () -> new ResourceNotFoundException("User","id",userId));
		return this.userToDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User> users = this.userRepo.findAll();
		List<UserDto> userDtos = users.stream().map(user-> this.userToDto(user)).collect(Collectors.toList());
		return userDtos;
	}

	@Override
	public void deleteUser(Integer userId) {
		User user=this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "id", userId));
		this.userRepo.delete(user);

	}

	@Override
	public Map<String, Object> createActor(String email, String publicKey) {
		if (email == null || publicKey == null) {
			throw new IllegalArgumentException("Email and public key must not be null");
		}

		// Using HashMap to allow conditional key-value addition and prevent null issues with Map.of()
		Map<String, Object> actor = new HashMap<>();
		actor.put("@context", "https://www.w3.org/ns/activitystreams");
		actor.put("id", "https://" + AppConstants.DOMAIN + "/users/" + email);
		actor.put("type", "Person");
		actor.put("preferredUsername", email.split("@")[0]);

		// PublicKey object construction with checks
		Map<String, String> publicKeyMap = new HashMap<>();
		publicKeyMap.put("id", "https://" + AppConstants.DOMAIN + "/users/" + email + "#main-key");
		publicKeyMap.put("owner", "https://" + AppConstants.DOMAIN + "/users/" + email);
		publicKeyMap.put("publicKeyPem", publicKey);

		actor.put("publicKey", publicKeyMap);

		return actor;
	}


	@Override
	public Map<String, Object> createWebfinger(String email) {
		Map<String, Object> webfinger = new HashMap<>();
		webfinger.put("subject", "acct:" + email);
		webfinger.put("links", new Object[]{
				Map.of(
						"rel", "self",
						"type", "application/activity+json",
						"href", "https://" + AppConstants.DOMAIN + "/users/" + email
				)
		});
		return webfinger;
	}

	@Override
	public Account saveAccount(UserDto userDto, Map<String, Object> actorRecord, Map<String, Object> webfingerRecord, String privateKey) {
		// Check if an account with the same email already exists
		Optional<Account> existingAccount = accountRepo.findByName(userDto.getEmail());
		if (existingAccount.isPresent()) {
			throw new RuntimeException("Account with email " + userDto.getEmail() + " already exists.");
		}

		// Create and populate the Account entity with ActivityPub and user information
		Account account = new Account();
		account.setName(userDto.getEmail());

		try {
			// Serialize actor and webfinger records to JSON strings
			String actorJson = objectMapper.writeValueAsString(actorRecord);
			String webfingerJson = objectMapper.writeValueAsString(webfingerRecord);

			// Set fields in the Account entity
			account.setActor(actorJson);
			account.setPubkey(actorRecord.get("publicKey").toString());
			account.setPrivkey(privateKey);
			account.setWebfinger(webfingerJson);
			account.setSummary(userDto.getAbout());

			// Save the Account entity
			return accountRepo.save(account);
		} catch (Exception e) {
			throw new RuntimeException("Failed to save account information", e);
		}
	}




	private User dtoToUser(UserDto userDto) {
		//User user = new User();
		User user = this.modelMapper.map(userDto, User.class);
		
		/*
		 * user.setId(userDto.getId()); user.setName(userDto.getName());
		 * user.setEmail(userDto.getEmail()); user.setAbout(userDto.getAbout());
		 * user.setPassword(userDto.getPassword());
		 */
		
		return user;
		
	}
	
	public UserDto userToDto(User user) {
		//UserDto userDto = new UserDto();
		UserDto userDto = this.modelMapper.map(user, UserDto.class);
		/*
		 * userDto.setId(user.getId()); userDto.setName(user.getName());
		 * userDto.setEmail(user.getEmail()); userDto.setAbout(user.getAbout());
		 * userDto.setPassword(user.getPassword());
		 */
		
		return userDto;
	}

	@Override
	public Account registerNewUser(UserDto userDto) {
		// Map userDto to User entity and save basic information
		User user = modelMapper.map(userDto, User.class);
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));

		// Save the User to the database
		User savedUser = userRepo.save(user);

		// Generate RSA Key Pair for ActivityPub
		KeyPair keyPair = generateKeyPair();
		String publicKey = encodeKey(keyPair.getPublic().getEncoded());
		String privateKey = encodeKey(keyPair.getPrivate().getEncoded());

		// Create Actor and Webfinger records for ActivityPub
		Map<String, Object> actorRecord = createActor(userDto.getEmail(), publicKey);
		Map<String, Object> webfingerRecord = createWebfinger(userDto.getEmail());

		// Save the Account entity with ActivityPub information
		return saveAccount(userDto, actorRecord, webfingerRecord, privateKey);
	}

	private KeyPair generateKeyPair() {
		try {
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
			keyGen.initialize(2048);
			return keyGen.generateKeyPair();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Failed to generate RSA key pair", e);
		}
	}

	private String encodeKey(byte[] key) {
		return Base64.getEncoder().encodeToString(key);
	}

}
