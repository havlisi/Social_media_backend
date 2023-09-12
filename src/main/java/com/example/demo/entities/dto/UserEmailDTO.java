package com.example.demo.entities.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public class UserEmailDTO {
	
	@NotNull(message = "Please provide email address.")
	@Email(message = "Email is not valid.")
	private String email;

	public UserEmailDTO() {
		super();
	}

	public UserEmailDTO(
			@NotNull(message = "Please provide email address.") @Email(message = "Email is not valid.") String email) {
		super();
		this.email = email;
	}
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
