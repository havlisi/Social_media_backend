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
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PostEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonProperty("ID")
	private Integer id;
	
	@Column
	private String title;
	
	@Column
	private String content;
	
	@Column
	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Comments> comments;
	
	@Column
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
	@JoinTable(name = "PostReactions", joinColumns = {
			@JoinColumn(name = "Post_id", nullable = false, updatable = false)} , inverseJoinColumns = {
					@JoinColumn(name = "Reaction_id", nullable = false, updatable = false) })
	private List<ReactionsEntity> reactions;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "regularUser")
	private RegularUserEntity regularUser;

	public PostEntity() {
		super();
	}

	public PostEntity(Integer id, String title, String content, List<Comments> comments,
			List<ReactionsEntity> reactions, RegularUserEntity regularUser) {
		super();
		this.id = id;
		this.title = title;
		this.content = content;
		this.comments = comments;
		this.reactions = reactions;
		this.regularUser = regularUser;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<Comments> getComments() {
		return comments;
	}

	public void setComments(List<Comments> comments) {
		this.comments = comments;
	}

	public List<ReactionsEntity> getReactions() {
		return reactions;
	}

	public void setReactions(List<ReactionsEntity> reactions) {
		this.reactions = reactions;
	}

	public RegularUserEntity getRegularUser() {
		return regularUser;
	}

	public void setRegularUser(RegularUserEntity regularUser) {
		this.regularUser = regularUser;
	}
	
}
