package com.example.demo.entities.dto;

import java.util.List;
import com.example.demo.entities.Comments;
import com.example.demo.entities.Post;
import com.example.demo.entities.Reactions;

public class PostDTO {
private Integer id;
	
	private String title;
	
	private String content;
	
	private List<Comments> comments;
	
	private List<Reactions> reactions;
	
	private Integer regularUser;

	public PostDTO() {
		super();
	}

	public PostDTO(Post p) {
		super();
		this.id = p.getId();
		this.title = p.getTitle();
		this.content = p.getContent();
		this.comments = p.getComments();
		this.reactions = p.getReactions();
		this.regularUser = p.getRegularUser().getId();
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

	public List<Reactions> getReactions() {
		return reactions;
	}

	public void setReactions(List<Reactions> reactions) {
		this.reactions = reactions;
	}

	public Integer getRegularUser() {
		return regularUser;
	}

	public void setRegularUser(Integer regularUser) {
		this.regularUser = regularUser;
	}

}
