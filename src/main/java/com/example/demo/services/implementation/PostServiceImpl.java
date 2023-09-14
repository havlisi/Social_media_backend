package com.example.demo.services.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.entities.Comment;
import com.example.demo.entities.Following;
import com.example.demo.entities.Post;
import com.example.demo.entities.RegularUser;
import com.example.demo.entities.dto.CommentDTO;
import com.example.demo.entities.dto.PostDTO;
import com.example.demo.exceptions.PostNotFoundException;
import com.example.demo.exceptions.UnauthorizedUserException;
import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.repositories.CommentRepository;
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
	
	@Autowired
	CommentRepository commentRepository;
	
	public List<Post> getAll() throws Exception {
		List<Post> posts = (List<Post>) postRepository.findAll();
		if (posts.isEmpty()) {
			throw new PostNotFoundException("No posts found");
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
		
		if(loggedUser.getRole().equals("ROLE_REGULAR_USER")) {
			Post post = new Post();
			post.setTitle(newPost.getTitle());
			post.setContent(newPost.getContent());
			post.setRegularUser(loggedUser);
			postRepository.save(post);
			return new PostDTO(post);
		}
		
		throw new UnauthorizedUserException("User is not authorized to update this user");
	}

	public CommentDTO addComment(Integer id, CommentDTO newComment, String name) throws Exception {
		
		RegularUser loggedUser = regularUserRepository.findByEmail(name);
		
		if(loggedUser.getRole().equals("ROLE_REGULAR_USER")) {
			Optional<Post> post = postRepository.findById(id);
			
			if (post.isEmpty()) {
				throw new PostNotFoundException("No posts found");
			}
			
			Comment comment = new Comment();
			comment.setComment(newComment.getComment());
			comment.setPost(post.get());
			comment.setRegularUser(loggedUser);
			commentRepository.save(comment);
			
			ArrayList<Comment> comments = new ArrayList<>(post.get().getComments());
			
			comments.add(comment);
			postRepository.save(post.get());
			
			return new CommentDTO(comment);
		}
		
		throw new UnauthorizedUserException("User is not authorized to update this user");
	}
	
	
}
