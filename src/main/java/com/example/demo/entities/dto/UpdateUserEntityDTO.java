package com.example.demo.entities.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import com.example.demo.entities.User;

public class UpdateUserEntityDTO {
	
	private Integer id;
	
	@Size(min = 2, max = 30, message = "First name must be between "
			+ "{min} and {max} characters long.")
	private String firstName;
	
	@Size(min = 2, max = 30, message = "Last name must be between "
			+ "{min} and {max} characters long.")
	private String lastName;

	@Size(min = 5, max = 25, message = "Username must be between "
			+ "{min} and {max} characters long.")
	private String username;
	
	@Email(message = "Email is not valid.")
	private String email;
	
	public UpdateUserEntityDTO() {
	}

	public UpdateUserEntityDTO(User u) {
		this.id = u.getId();
		this.firstName = u.getFirstName();
		this.lastName = u.getLastName();
		this.email = u.getEmail();
		this.username = u.getUsername();
		this.firstName = u.getFirstName();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
