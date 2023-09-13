package com.example.demo.entities;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ReactionsEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonProperty("ID")
	private Integer id;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
	@JoinTable(name = "PostReactions", joinColumns = {
			@JoinColumn(name = "Reaction_id", nullable = false, updatable = false)} , inverseJoinColumns = {
					@JoinColumn(name = "Post_id", nullable = false, updatable = false) })
	private List<PostEntity> post;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
	@JoinTable(name = "UserReactions", joinColumns = {
			@JoinColumn(name = "Reaction_id", nullable = false, updatable = false)} , inverseJoinColumns = {
					@JoinColumn(name = "User_id", nullable = false, updatable = false) })
	private List<RegularUserEntity> regularUser;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
	@JoinTable(name = "CommentReactions", joinColumns = {
			@JoinColumn(name = "Reaction_id", nullable = false, updatable = false)} , inverseJoinColumns = {
					@JoinColumn(name = "Comment_id", nullable = false, updatable = false) })
	private List<Comments> comments;

	public ReactionsEntity() {
		super();
	}

	public ReactionsEntity(Integer id, List<PostEntity> post, List<RegularUserEntity> regularUser,
			List<Comments> comments) {
		super();
		this.id = id;
		this.post = post;
		this.regularUser = regularUser;
		this.comments = comments;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<PostEntity> getPost() {
		return post;
	}

	public void setPost(List<PostEntity> post) {
		this.post = post;
	}

	public List<RegularUserEntity> getRegularUser() {
		return regularUser;
	}

	public void setRegularUser(List<RegularUserEntity> regularUser) {
		this.regularUser = regularUser;
	}

	public List<Comments> getComments() {
		return comments;
	}

	public void setComments(List<Comments> comments) {
		this.comments = comments;
	}
	
}
