package com.example.demo.entities;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Comments {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonProperty("ID")
	private Integer id;
	
	private String comment;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "regularUser")
	private RegularUserEntity regularUser;
	
	@OneToMany(mappedBy = "comments", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
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
