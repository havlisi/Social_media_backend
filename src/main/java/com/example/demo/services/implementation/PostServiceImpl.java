package com.example.demo.services.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.entities.Comment;
import com.example.demo.entities.Following;
import com.example.demo.entities.Post;
import com.example.demo.entities.Reaction;
import com.example.demo.entities.ReactionEnum;
import com.example.demo.entities.RegularUser;
import com.example.demo.entities.dto.CommentDTO;
import com.example.demo.entities.dto.PostDTO;
import com.example.demo.exceptions.PostNotFoundException;
import com.example.demo.exceptions.UnauthorizedUserException;
import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.repositories.CommentRepository;
import com.example.demo.repositories.FollowingRepository;
import com.example.demo.repositories.PostRepository;
import com.example.demo.repositories.ReactionRepository;
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
	
	@Autowired
	ReactionRepository reactionRepository;
	
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
			post.setComments(null);
			postRepository.save(post);
			return new PostDTO(post);
		}
		
		throw new UnauthorizedUserException("User is not authorized to update this user");
	}

	public PostDTO addComment(Integer id, CommentDTO newComment, String name) throws Exception {
		
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
			
			ArrayList<Comment> postComments = new ArrayList<>(post.get().getComments());
			postComments.add(comment);
			postRepository.save(post.get());
			
			return new PostDTO(post.get());
		}
		
		throw new UnauthorizedUserException("User is not authorized to update this user");
	}

	public PostDTO addLikeReaction(Integer id, String name) throws Exception {
		
		RegularUser loggedUser = regularUserRepository.findByEmail(name);
		
		if(loggedUser.getRole().equals("ROLE_REGULAR_USER")) {
			
			Optional<Post> post = postRepository.findById(id);
			
			if (post.isEmpty()) {
				throw new PostNotFoundException("No posts found");
			}
			
		
			Optional<Reaction> existingReaction = reactionRepository.findByPostAndRegularUser(post.get(), loggedUser);
			
			if (existingReaction.isEmpty()) {
				Reaction reaction = new Reaction();
				reaction.setPost(post.get());
				reaction.setRegularUser(loggedUser);
				reaction.setReactionType(ReactionEnum.LIKE);

				reactionRepository.save(reaction);
				ArrayList<Reaction> postReactions = new ArrayList<>(post.get().getReactions());
				postReactions.add(reaction);
				postRepository.save(post.get());
				
			} else if (existingReaction.get().getReactionType() == null || existingReaction.get().getReactionType() == ReactionEnum.DISLIKE) {
				existingReaction.get().setReactionType(ReactionEnum.LIKE);

				reactionRepository.save(existingReaction.get());
				ArrayList<Reaction> postReactions = new ArrayList<>(post.get().getReactions());
				postReactions.add(existingReaction.get());
				postRepository.save(post.get());
				
			} else {
				existingReaction.get().setReactionType(null);
				reactionRepository.save(existingReaction.get());
				postRepository.save(post.get());
			}
			
			return new PostDTO(post.get());
		}
		
		throw new UnauthorizedUserException("User is not authorized to update this user");
	}
	
	public PostDTO addDislikeReaction(Integer id, String name) throws Exception {
			
			RegularUser loggedUser = regularUserRepository.findByEmail(name);
			
			if(loggedUser.getRole().equals("ROLE_REGULAR_USER")) {
				
				Optional<Post> post = postRepository.findById(id);
				
				if (post.isEmpty()) {
					throw new PostNotFoundException("No posts found");
				}
				
			
				Optional<Reaction> existingReaction = reactionRepository.findByPostAndRegularUser(post.get(), loggedUser);
				
				if (existingReaction.isEmpty()) {
					Reaction reaction = new Reaction();
					reaction.setPost(post.get());
					reaction.setRegularUser(loggedUser);
					reaction.setReactionType(ReactionEnum.DISLIKE);

					reactionRepository.save(reaction);
					ArrayList<Reaction> postReactions = new ArrayList<>(post.get().getReactions());
					postReactions.add(reaction);
					postRepository.save(post.get());
					
				} else if (existingReaction.get().getReactionType() == null || existingReaction.get().getReactionType() == ReactionEnum.LIKE) {
					existingReaction.get().setReactionType(ReactionEnum.DISLIKE);

					reactionRepository.save(existingReaction.get());
					ArrayList<Reaction> postReactions = new ArrayList<>(post.get().getReactions());
					postReactions.add(existingReaction.get());
					postRepository.save(post.get());
					
				} else {
					existingReaction.get().setReactionType(null);
					reactionRepository.save(existingReaction.get());
					postRepository.save(post.get());
				}
				
				return new PostDTO(post.get());
			}
			
			throw new UnauthorizedUserException("User is not authorized to update this user");
		}
	
}
