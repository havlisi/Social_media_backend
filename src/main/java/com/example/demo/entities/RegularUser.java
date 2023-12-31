package com.example.demo.entities;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "regular_user")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class RegularUser extends User {
	
	@OneToMany(mappedBy = "regularUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Post> posts;
	
	@OneToMany(mappedBy = "regularUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Comment> comments;
	
	@OneToMany(mappedBy = "regularUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Reaction> reactions;
	
	@OneToMany(mappedBy = "follower", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Following> followers;
	
	@OneToMany(mappedBy = "followee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Following> followees;
	
	public RegularUser() {
		super();
	}

	public RegularUser(List<Post> posts, List<Comment> comments, List<Reaction> reactions,
			List<Following> followers, List<Following> followees) {
		super();
		this.posts = posts;
		this.comments = comments;
		this.reactions = reactions;
		this.followers = followers;
		this.followees = followees;
	}

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public List<Reaction> getReactions() {
		return reactions;
	}

	public void setReactions(List<Reaction> reactions) {
		this.reactions = reactions;
	}

	public List<Following> getFollowers() {
		return followers;
	}

	public void setFollowers(List<Following> followers) {
		this.followers = followers;
	}

	public List<Following> getFollowees() {
		return followees;
	}

	public void setFollowees(List<Following> followees) {
		this.followees = followees;
	}
	
}
