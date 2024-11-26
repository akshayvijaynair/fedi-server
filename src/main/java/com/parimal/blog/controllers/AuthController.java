package com.parimal.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.parimal.blog.entities.Account;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.parimal.blog.exceptions.ApiException;
import com.parimal.blog.payloads.JwtAuthRequest;
import com.parimal.blog.payloads.JwtAuthResponse;
import com.parimal.blog.payloads.UserDto;
import com.parimal.blog.security.JwtTokenHelper;
import com.parimal.blog.services.UserService;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1/auth/")
public class AuthController {

	@Autowired
	private JwtTokenHelper jwtTokenHelper;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService userService;

	@Autowired
	private ObjectMapper objectMapper;

	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) throws Exception {
		this.authenticate(request.getUsername(), request.getPassword());
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());
		String token = this.jwtTokenHelper.generateToken(userDetails);

		JwtAuthResponse response = new JwtAuthResponse();
		response.setToken(token);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	private void authenticate(String username, String password) throws Exception {
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
		try {
			this.authenticationManager.authenticate(authenticationToken);
		} catch (BadCredentialsException e) {
			System.out.println("Invalid Details!");
			throw new ApiException("Invalid username or password!");
		}
	}

	// Register new user API with ActivityPub integration
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestBody UserDto userDto) {
		try {
			// Register user and get the created Account with ActivityPub details
			Account account = userService.registerNewUser(userDto);

			// Prepare a response map with account details
			Map<String, Object> response = new HashMap<>();
			response.put("message", "User registered successfully");
			response.put("account", account);

			return new ResponseEntity<>(response, HttpStatus.CREATED);
		} catch (RuntimeException e) {
			// Handle duplicate account or any other registration error gracefully
			if (e.getMessage().contains("already exists")) {
				return ResponseEntity.status(HttpStatus.CONFLICT)
						.body(Map.of("error", "Account with email " + userDto.getEmail() + " already exists."));
			}
			// For other exceptions, return a general error message
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Map.of("error", "Failed to register user. Please try again later."));
		}
	}
}
