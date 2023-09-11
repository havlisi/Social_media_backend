package com.example.demo.entities.dto;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import com.example.demo.entities.PostEntity;
import com.example.demo.entities.RegularUserEntity;

public class RegularUserEntityDTO {
	
	private Integer id;
	
	@NotNull(message = "First name must be provided.")
	@Size(min = 2, max = 30, message = "First name must be between "
			+ "{min} and {max} characters long.")
	private String firstName;
	
	@NotNull(message = "Last name must be provided.")
	@Size(min = 2, max = 30, message = "Last name must be between "
			+ "{min} and {max} characters long.")
	private String lastName;

	@NotNull(message = "Username must be provided.")
	@Size(min = 5, max = 25, message = "Username must be between "
			+ "{min} and {max} characters long.")
	private String username;
	
	@NotNull(message = "Please provide email address.")
	@Email(message = "Email is not valid.")
	private String email;
	
	@NotNull(message = "Password must be provided.")
	@Size(min = 5, message = "Password must be minimum "+ "{min} characters long.")

//	@Pattern(regexp = "(?=.*?[#?!@$%^&*-\]\[])", message = "Password must include at least one special character")
	private String password;
	
//	@Pattern(regexp = "^(?=.*[@#$%^&+=])\\S+$", message = "Password must include at least one special character")
	private String confirmed_password;
	
	@Column(nullable = false)
	private String role;
	
	@Column
	private List<PostEntity> posts;
	
	public RegularUserEntityDTO() {
	}

	public RegularUserEntityDTO(RegularUserEntity u) {
		this.id = u.getId();
		this.firstName = u.getFirstName();
		this.lastName = u.getLastName();
		this.email = u.getEmail();
		this.password = u.getPassword();
		this.confirmed_password = u.getConfirmed_password();
		this.firstName = u.getFirstName();
		this.role = u.getRole();
		List<PostEntity> posts = new ArrayList<>();
		for (PostEntity post : u.getPosts()) {
			posts.add(post);
		}
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public List<PostEntity> getPosts() {
		return posts;
	}

	public void setPosts(List<PostEntity> posts) {
		this.posts = posts;
	}

	public String getConfirmed_password() {
		return confirmed_password;
	}

	public void setConfirmed_password(String confirmed_password) {
		this.confirmed_password = confirmed_password;
	}
}