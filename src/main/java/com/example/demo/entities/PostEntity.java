package com.example.demo.entities;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PostEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonProperty("ID")
	private Integer id;
	
	@Column
	@NotNull(message = "URL must be included.")
	private String url;
	
	@Column
	private String description;
	
	@Column
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
	@JoinTable(name = "PostReactions", joinColumns = {
			@JoinColumn(name = "Post_id", nullable = false, updatable = false)} , inverseJoinColumns = {
					@JoinColumn(name = "Reaction_id", nullable = false, updatable = false) })
	private List<ReactionsEntity> reactions;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "user")
	private UserEntity user;

	public PostEntity() {
		super();
	}

	public PostEntity(Integer id, @NotNull(message = "URL must be included.") String url, String description,
			List<ReactionsEntity> reactions, UserEntity user) {
		super();
		this.id = id;
		this.url = url;
		this.description = description;
		this.reactions = reactions;
		this.user = user;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<ReactionsEntity> getReactions() {
		return reactions;
	}

	public void setReactions(List<ReactionsEntity> reactions) {
		this.reactions = reactions;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}
	
}
