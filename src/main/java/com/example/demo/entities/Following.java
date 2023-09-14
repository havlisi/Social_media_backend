package com.example.demo.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "following_users")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Following {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "follower")
	private RegularUser follower;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "followee")
	private RegularUser followee;

	public Following() {
		super();
	}
	
	public Following(RegularUser follower, RegularUser followee) {
		super();
		this.follower = follower;
		this.followee = followee;
	}

	public Following(Integer id, RegularUser follower, RegularUser followee) {
		super();
		this.id = id;
		this.follower = follower;
		this.followee = followee;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public RegularUser getFollower() {
		return follower;
	}

	public void setFollower(RegularUser follower) {
		this.follower = follower;
	}

	public RegularUser getFollowee() {
		return followee;
	}

	public void setFollowee(RegularUser followee) {
		this.followee = followee;
	}

}
