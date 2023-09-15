package com.example.demo.entities.dto;

import java.util.List;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import com.example.demo.entities.Post;
import com.example.demo.entities.RegularUser;

public class RegularUserDTO {
	
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
	private String confirmedPassword;
	
	private String role;
	
	private List<PostDTO> posts;
	
	public RegularUserDTO() {
	}

	public RegularUserDTO(RegularUser u, String confirmedPassword) {
		this.id = u.getId();
		this.firstName = u.getFirstName();
		this.lastName = u.getLastName();
		this.email = u.getEmail();
		this.password = u.getPassword();
		this.confirmedPassword = confirmedPassword;
		this.username = u.getUsername();
		this.role = u.getRole();
		for (Post post : u.getPosts()) {
			PostDTO postDTO = new PostDTO(post);
			this.posts.add(postDTO);
		}
	}

	public RegularUserDTO(RegularUser u) {
		this.id = u.getId();
		this.firstName = u.getFirstName();
		this.lastName = u.getLastName();
		this.email = u.getEmail();
		this.password = u.getPassword();
		this.username = u.getUsername();
		this.role = u.getRole();
		for (Post post : u.getPosts()) {
			PostDTO postDTO = new PostDTO(post);
			this.posts.add(postDTO);
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

	public List<PostDTO> getPosts() {
		return posts;
	}

	public void setPosts(List<PostDTO> posts) {
		this.posts = posts;
	}

	public String getConfirmedPassword() {
		return confirmedPassword;
	}

	public void setConfirmedPassword(String confirmedPassword) {
		this.confirmedPassword = confirmedPassword;
	}
}
