package com.griffinfore.LoginAndRegistration2.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class LoginUser {
	
	@NotBlank
	@Email(message="Must be a valid email")
	private String email;
	
	@NotBlank
	@Size(min=8, message="Password must be at least 8 characters")
	private String password;
	
	public LoginUser() {
		
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
