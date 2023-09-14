package com.example.demo.entities.dto;

import com.example.demo.entities.Reaction;
import com.example.demo.entities.ReactionEnum;

public class ReactionDTO {
	
	private Integer id;
	
	private Integer regularUser;
	
	private ReactionEnum reactionType;
	
	public ReactionDTO() {
		super();
	}

	public ReactionDTO(Reaction r) {
		super();
		this.id = r.getId();
		this.regularUser = r.getRegularUser().getId();
		this.reactionType = r.getReactionType();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRegularUser() {
		return regularUser;
	}

	public void setRegularUser(Integer regularUser) {
		this.regularUser = regularUser;
	}

	public ReactionEnum getReactionType() {
		return reactionType;
	}

	public void setReactionType(ReactionEnum reactionType) {
		this.reactionType = reactionType;
	}

}
