package com.example.demo.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "following_users")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Following {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	private RegularUserEntity followerId;
	private RegularUserEntity followeeId;
	
	public Following() {
		super();
	}

	public Following(RegularUserEntity followerId, RegularUserEntity followeeId) {
		super();
		this.followerId = followerId;
		this.followeeId = followeeId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public RegularUserEntity getFollowerId() {
		return followerId;
	}

	public void setFollowerId(RegularUserEntity followerId) {
		this.followerId = followerId;
	}

	public RegularUserEntity getFolloweeId() {
		return followeeId;
	}

	public void setFolloweeId(RegularUserEntity followeeId) {
		this.followeeId = followeeId;
	}

}
