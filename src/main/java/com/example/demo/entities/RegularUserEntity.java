package com.example.demo.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "regular_user")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class RegularUserEntity extends UserEntity {
	
	@Column
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<PostEntity> posts;

	public RegularUserEntity() {
		super();
	}

	public RegularUserEntity(Integer id,
			@NotNull(message = "First name must be provided.") @Size(min = 2, max = 30, message = "First name must be between {min} and {max} characters long.") String firstName,
			@NotNull(message = "Last name must be provided.") @Size(min = 2, max = 30, message = "Last name must be between {min} and {max} characters long.") String lastName,
			@NotNull(message = "Username must be provided.") @Size(min = 5, max = 25, message = "Username must be between {min} and {max} characters long.") String username,
			@NotNull(message = "Please provide email address.") @Email(message = "Email is not valid.") String email,
			@NotNull(message = "Password must be provided.") @Size(min = 5, message = "Password must be minimum {min} characters long.") String password,
			String confirmed_password, String role) {
		super(id, firstName, lastName, username, email, password, confirmed_password, role);
	}

	public List<PostEntity> getPosts() {
		return posts;
	}

	public void setPosts(List<PostEntity> posts) {
		this.posts = posts;
	}
	
}
