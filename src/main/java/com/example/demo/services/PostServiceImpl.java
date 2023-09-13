package com.example.demo.services;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.example.demo.entities.Following;
import com.example.demo.entities.PostEntity;
import com.example.demo.entities.RegularUserEntity;
import com.example.demo.entities.dto.PostDTO;
import com.example.demo.exceptions.UnauthorizedUserException;
import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.repositories.FollowingRepository;
import com.example.demo.repositories.PostRepository;
import com.example.demo.repositories.RegularUserRepository;

@Service
public class PostServiceImpl implements PostService {
	
	@Autowired
	PostRepository postRepository;
	
	@Autowired
	RegularUserRepository regularUserRepository;
	
	@Autowired
	FollowingRepository followingRepository;

	// TODO: ispraviti sve nazive metoda da budu kraci
	
	public List<PostEntity> getAll() throws Exception {
		List<PostEntity> posts = (List<PostEntity>) postRepository.findAll();
		if (posts.isEmpty()) {
			throw new UserNotFoundException("No posts found");
		}
		return posts;
	}

	public List<PostEntity> getAllPostsForHomePage(Authentication authentication) throws Exception {
		
		String email = authentication.getName();
		RegularUserEntity loggedUser = regularUserRepository.findByEmail(email);
			
		List<PostEntity> posts = new ArrayList<>();
		
		List<Following> followees = followingRepository.findAllByFollowerId(loggedUser.getId());
		
		if (followees.isEmpty()) {
			throw new UserNotFoundException("No followees found in the database");
		}
		
		for (Following followee : followees) {
			posts.addAll(followee.getFollowee().getPosts()); 
		}
		
		return posts;

	}
	
	@RequestMapping(method = RequestMethod.POST)
	public PostDTO createPost(@RequestBody PostDTO newPost, Authentication authentication) throws Exception {
		 
		String email = authentication.getName();
		RegularUserEntity loggedUser = regularUserRepository.findByEmail(email);
		
		PostEntity post = new PostEntity();
		
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
