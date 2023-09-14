package com.example.demo.entities.dto;

import com.example.demo.entities.Comment;

public class CommentDTO {

	private String comment;

	public CommentDTO() {
		super();
	}
	
	public CommentDTO(Comment c) {
		super();
		this.comment = c.getComment();
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
}
