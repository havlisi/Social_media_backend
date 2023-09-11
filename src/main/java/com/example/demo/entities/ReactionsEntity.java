package com.example.demo.entities;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ReactionsEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonProperty("ID")
	private Integer id;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
	@JoinTable(name = "PostReactions", joinColumns = {
			@JoinColumn(name = "Reaction_id", nullable = false, updatable = false)} , inverseJoinColumns = {
					@JoinColumn(name = "Post_id", nullable = false, updatable = false) })
	private List<PostEntity> post;

	public ReactionsEntity() {
		super();
	}

	public ReactionsEntity(Integer id, List<PostEntity> post) {
		super();
		this.id = id;
		this.post = post;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<PostEntity> getPost() {
		return post;
	}

	public void setPost(List<PostEntity> post) {
		this.post = post;
	}
	
}
