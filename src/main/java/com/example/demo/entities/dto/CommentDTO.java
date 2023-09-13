package com.example.demo.entities.dto;

public class CommentDTO {

	private String comment;

	public CommentDTO() {
		super();
	}
	
	public CommentDTO(String comment) {
		super();
		this.comment = comment;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
}
