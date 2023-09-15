package com.example.demo.entities.dto;

import java.util.List;
import com.example.demo.entities.Comment;
import com.example.demo.entities.Post;
import com.example.demo.entities.Reaction;

public class PostDTO {
	
	private Integer id;
	
	private String title;
	
	private String content;
	
	private List<CommentDTO> comments;
	
	private List<ReactionDTO> reactions;
	
	private Integer regularUser;

	public PostDTO() {
		super();
	}

	public PostDTO(Post p) {
		super();
		this.id = p.getId();
		this.title = p.getTitle();
		this.content = p.getContent();
		this.regularUser = p.getRegularUser().getId();
		for (Comment comment : p.getComments()) {
			CommentDTO commentDTO = new CommentDTO(comment);
			this.comments.add(commentDTO);
		}
		for (Reaction reaction : p.getReactions()) {
			ReactionDTO reactionDTO = new ReactionDTO(reaction);
			this.reactions.add(reactionDTO);
		}
	}

	public PostDTO(Integer id, String title, String content, List<CommentDTO> comments, List<ReactionDTO> reactions,
			Integer regularUser) {
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
	
	public Integer getRegularUser() {
		return regularUser;
	}

	public void setRegularUser(Integer regularUser) {
		this.regularUser = regularUser;
	}

	public List<CommentDTO> getComments() {
		return comments;
	}

	public void setComments(List<CommentDTO> comments) {
		this.comments = comments;
	}

	public List<ReactionDTO> getReactions() {
		return reactions;
	}

	public void setReactions(List<ReactionDTO> reactions) {
		this.reactions = reactions;
	}

}
