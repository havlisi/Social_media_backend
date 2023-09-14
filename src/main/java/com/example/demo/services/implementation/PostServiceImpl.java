package com.example.demo.services.implementation;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.entities.Following;
import com.example.demo.entities.Post;
import com.example.demo.entities.RegularUser;
import com.example.demo.entities.dto.PostDTO;
import com.example.demo.exceptions.UnauthorizedUserException;
import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.repositories.FollowingRepository;
import com.example.demo.repositories.PostRepository;
import com.example.demo.repositories.RegularUserRepository;
import com.example.demo.services.PostService;

@Service
public class PostServiceImpl implements PostService {
	
	@Autowired
	PostRepository postRepository;
	
	@Autowired
	RegularUserRepository regularUserRepository;
	
	@Autowired
	FollowingRepository followingRepository;
	
	public List<Post> getAll() throws Exception {
		List<Post> posts = (List<Post>) postRepository.findAll();
		if (posts.isEmpty()) {
			throw new UserNotFoundException("No posts found");
		}
		return posts;
	}

	public List<Post> getAllForHomePage(String name) throws Exception {
		
		RegularUser loggedUser = regularUserRepository.findByEmail(name);
		
		List<Post> posts = new ArrayList<>();
		
		List<Following> followees = followingRepository.findAllByFollowerId(loggedUser.getId());
		
		if (followees.isEmpty()) {
			throw new UserNotFoundException("No followees found in the database");
		}
		
		for (Following followee : followees) {
			posts.addAll(followee.getFollowee().getPosts()); 
		}
		
		return posts;

	}
	
	public PostDTO create(PostDTO newPost, String name) throws Exception {
		 
		RegularUser loggedUser = regularUserRepository.findByEmail(name);
		
		Post post = new Post();
		
		if(loggedUser.getRole().equals("ROLE_REGULAR_USER")) {
		
			post.setTitle(newPost.getTitle());
			post.setContent(newPost.getContent());
			post.setRegularUser(loggedUser);
			postRepository.save(post);
			return new PostDTO(post);
		}
		
		throw new UnauthorizedUserException("User is not authorized to update this user");
	}
	
}
