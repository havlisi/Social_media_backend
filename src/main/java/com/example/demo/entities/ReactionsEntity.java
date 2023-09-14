package com.example.demo.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ReactionsEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonProperty("ID")
	private Integer id;
	
	private String reaction; //TODO u kojoj formi da bude reaction
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "post")
	private PostEntity post;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "regularUser")
	private RegularUserEntity regularUser;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "comments")
	private Comments comments;
	
	public ReactionsEntity() {
		super();
	}

	public ReactionsEntity(Integer id, String reaction, PostEntity post, RegularUserEntity regularUser,
			Comments comments) {
		super();
		this.id = id;
		this.reaction = reaction;
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

	public String getReaction() {
		return reaction;
	}

	public void setReaction(String reaction) {
		this.reaction = reaction;
	}

	public PostEntity getPost() {
		return post;
	}

	public void setPost(PostEntity post) {
		this.post = post;
	}

	public RegularUserEntity getRegularUser() {
		return regularUser;
	}

	public void setRegularUser(RegularUserEntity regularUser) {
		this.regularUser = regularUser;
	}

	public Comments getComments() {
		return comments;
	}

	public void setComments(Comments comments) {
		this.comments = comments;
	}
	
}
