package com.example.demo.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Comments {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonProperty("ID")
	private Integer id;
	
	@Column
	private String comment;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "regularUser")
	private RegularUserEntity regularUser;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
	@JoinTable(name = "CommentReactions", joinColumns = {
			@JoinColumn(name = "Reaction_id", nullable = false, updatable = false)} , inverseJoinColumns = {
					@JoinColumn(name = "Comment_id", nullable = false, updatable = false) })
	private List<ReactionsEntity> reactions;

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "post")
	private PostEntity post;

	public Comments() {
		super();
	}

	public Comments(Integer id, String comment, RegularUserEntity regularUser, List<ReactionsEntity> reactions,
			PostEntity post) {
		super();
		this.id = id;
		this.comment = comment;
		this.regularUser = regularUser;
		this.reactions = reactions;
		this.post = post;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public RegularUserEntity getRegularUser() {
		return regularUser;
	}

	public void setRegularUser(RegularUserEntity regularUser) {
		this.regularUser = regularUser;
	}

	public List<ReactionsEntity> getReactions() {
		return reactions;
	}

	public void setReactions(List<ReactionsEntity> reactions) {
		this.reactions = reactions;
	}

	public PostEntity getPost() {
		return post;
	}

	public void setPost(PostEntity post) {
		this.post = post;
	}

}
