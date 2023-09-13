package com.example.demo.services;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import com.example.demo.entities.PostEntity;
import com.example.demo.entities.RegularUserEntity;
import com.example.demo.exceptions.PostNotFoundException;
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
		
		if (posts.isEmpty()) {
			throw new PostNotFoundException("No posts found in the database");
		}
		
		List<RegularUserEntity> followees = followingRepository.findAllByFollowerId(loggedUser.getId());
		
		if (followees.isEmpty()) {
			throw new UserNotFoundException("No followees found in the database");
		}
		
		for (RegularUserEntity followee : followees) {
			posts.addAll(followee.getPosts()); 
		}
		
		return posts;

	}

	

}
