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
	private List<Comments> comments;
	
	@OneToMany(mappedBy = "regularUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Reactions> reactions;
	
	@OneToMany(mappedBy = "follower", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Following> followers;
	
	@OneToMany(mappedBy = "followee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Following> followees;
	
	public RegularUser() {
		super();
	}

	public RegularUser(List<Post> posts, List<Comments> comments, List<Reactions> reactions,
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

	public List<Comments> getComments() {
		return comments;
	}

	public void setComments(List<Comments> comments) {
		this.comments = comments;
	}

	public List<Reactions> getReactions() {
		return reactions;
	}

	public void setReactions(List<Reactions> reactions) {
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
