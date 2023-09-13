package com.example.demo.entities;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "Following")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Following {
	
	@Column
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
	@JoinTable(name = "PostReactions", joinColumns = {
			@JoinColumn(name = "Following_id", nullable = false, updatable = false)} , inverseJoinColumns = {
					@JoinColumn(name = "RegularUser_id", nullable = false, updatable = false) })
	private List<RegularUserEntity> regularUsers;

	public Following() {
		super();
	}
	
	public Following(List<RegularUserEntity> regularUsers) {
		super();
		this.regularUsers = regularUsers;
	}

	public List<RegularUserEntity> getRegularUsers() {
		return regularUsers;
	}

	public void setRegularUsers(List<RegularUserEntity> regularUsers) {
		this.regularUsers = regularUsers;
	}

}
