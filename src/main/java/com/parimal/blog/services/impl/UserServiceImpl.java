package com.parimal.blog.services.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.parimal.blog.config.AppConstants;
import com.parimal.blog.entities.Account;
import com.parimal.blog.entities.User;
import com.parimal.blog.exceptions.ResourceNotFoundException;
import com.parimal.blog.payloads.UserDto;
import com.parimal.blog.repositories.AccountRepo;
import com.parimal.blog.repositories.RoleRepo;
import com.parimal.blog.repositories.UserRepo;
import com.parimal.blog.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private AccountRepo accountRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private ObjectMapper objectMapper;

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
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(passwordEncoder.encode(userDto.getPassword())); // Encrypt the password
		user.setAbout(userDto.getAbout());

		User updatedUser = this.userRepo.save(user);
		return this.userToDto(updatedUser);
	}

	@Override
	public UserDto getUserById(Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
		return this.userToDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User> users = userRepo.findAll();
		return users.stream().map(this::userToDto).collect(Collectors.toList());
	}

	@Override
	public void deleteUser(Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
		userRepo.delete(user);
	}

	@Override
	public Account registerNewUser(UserDto userDto) {
		KeyPair keyPair = generateKeyPair();
		String publicKey = encodeKey(keyPair.getPublic().getEncoded());
		String privateKey = encodeKey(keyPair.getPrivate().getEncoded());

		Map<String, Object> actorRecord = createActor(userDto.getEmail(), publicKey);
		Map<String, Object> webfingerRecord = createWebfinger(userDto.getEmail());

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

	@Override
	public Map<String, Object> createActor(String email, String publicKey) {
		Map<String, Object> actor = new HashMap<>();
		actor.put("@context", "https://www.w3.org/ns/activitystreams");
		actor.put("id", "https://" + AppConstants.DOMAIN + "/users/" + email);
		actor.put("type", "Person");
		actor.put("preferredUsername", email.split("@")[0]);

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

		Map<String, String> link = new HashMap<>();
		link.put("rel", "self");
		link.put("type", "application/activity+json");
		link.put("href", "https://" + AppConstants.DOMAIN + "/users/" + email);

		webfinger.put("links", Collections.singletonList(link));

		return webfinger;
	}

	@Override
	public Account saveAccount(UserDto userDto, Map<String, Object> actorRecord, Map<String, Object> webfingerRecord, String privateKey) {
		Optional<Account> existingAccount = accountRepo.findByName(userDto.getEmail());
		if (existingAccount.isPresent()) {
			throw new RuntimeException("Account with email " + userDto.getEmail() + " already exists.");
		}

		Account account = new Account();
		account.setName(userDto.getEmail());

		try {
			JsonNode actorJson = objectMapper.valueToTree(actorRecord);
			JsonNode webfingerJson = objectMapper.valueToTree(webfingerRecord);
			JsonNode pubkeyJson = actorJson.get("publicKey");

			account.setActor(actorJson);
			account.setPubkey(pubkeyJson);
			account.setPrivkey(objectMapper.convertValue(privateKey, JsonNode.class));
			account.setWebfinger(webfingerJson);
			account.setSummary(userDto.getAbout());

			return accountRepo.save(account);
		} catch (Exception e) {
			throw new RuntimeException("Failed to save account information", e);
		}
	}

	private User dtoToUser(UserDto userDto) {
		return this.modelMapper.map(userDto, User.class);
	}

	private UserDto userToDto(User user) {
		return this.modelMapper.map(user, UserDto.class);
	}
}
