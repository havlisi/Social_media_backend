package com.example.demo.entities.dto;

public class ReactionDTO {
	
	private Integer id;
	
	private String reaction;
	
	private Integer regularUser;
	
	public ReactionDTO() {
		super();
	}

	public ReactionDTO(Integer id, String reaction, Integer regularUser) {
		super();
		this.id = id;
		this.reaction = reaction;
		this.regularUser = regularUser;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getReaction() {
		return reaction;
	}

	public void setReaction(String reaction) {
		this.reaction = reaction;
	}

	public Integer getRegularUser() {
		return regularUser;
	}

	public void setRegularUser(Integer regularUser) {
		this.regularUser = regularUser;
	}

}
