package com.parimal.blog.payloads;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {

	private int id;

	@NotEmpty
	@Size(min = 4, message = "Name must be at least 4 characters")
	private String name;

	@Email(message = "Invalid email format")
	private String email;

	@NotEmpty
	@Size(min = 3, max = 10, message = "Password should be between 3 and 10 characters")
	private String password;

	// Removed fields related to roles, keys, and ActivityPub, as they will be handled in Account
}
