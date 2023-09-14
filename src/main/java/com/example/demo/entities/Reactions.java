package com.example.demo.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Reactions {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonProperty("ID")
	private Integer id;
	
	private Boolean isLiked;
	
	private Boolean isDisiked;
	
	private enum Reaction {LIKE, DISLIKE}

	@JsonIgnore
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "post")
	private Post post;
	
	@JsonIgnore
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "regularUser")
	private RegularUser regularUser;
	
	@JsonIgnore
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "comments")
	private Comment comments;
	
	public Reactions() {
		super();
	}

	public Reactions(Integer id, Boolean isLiked, Boolean isDisiked, Post post, RegularUser regularUser,
			Comment comments) {
		super();
		this.id = id;
		this.isLiked = isLiked;
		this.isDisiked = isDisiked;
		this.post = post;
		this.regularUser = regularUser;
		this.comments = comments;
	}

	public Boolean getIsDisiked() {
		if (Reaction.DISLIKE != null) {
			return true;
		}
		return false;
	}

	public void setIsDisiked(Boolean isDisiked) {
		this.isDisiked = isDisiked;
	}

	public Boolean getIsLiked() {
		return isLiked;
	}

	public void setIsLiked(Boolean isLiked) {
		this.isLiked = isLiked;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public RegularUser getRegularUser() {
		return regularUser;
	}

	public void setRegularUser(RegularUser regularUser) {
		this.regularUser = regularUser;
	}

	public Comment getComments() {
		return comments;
	}

	public void setComments(Comment comments) {
		this.comments = comments;
	}

}
