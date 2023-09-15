package com.example.demo.services.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import com.example.demo.exceptions.TitleNullException;
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
	
	//private final
	PostRepository postRepository;
	
	RegularUserRepository regularUserRepository;
	
	FollowingRepository followingRepository;
	
	CommentRepository commentRepository;
	
	ReactionRepository reactionRepository;
	
	
	
	public PostServiceImpl(PostRepository postRepository, RegularUserRepository regularUserRepository,
			FollowingRepository followingRepository, CommentRepository commentRepository,
			ReactionRepository reactionRepository) {
		super();
		this.postRepository = postRepository;
		this.regularUserRepository = regularUserRepository;
		this.followingRepository = followingRepository;
		this.commentRepository = commentRepository;
		this.reactionRepository = reactionRepository;
	}

	public List<Post> getAll() throws Exception {
		List<Post> posts = (List<Post>) postRepository.findAll();
		
		if (posts.isEmpty()) {
			throw new PostNotFoundException("No posts found");
		}
		
		return posts;
	}

	public List<PostDTO> getAllForHomePage(String name) throws Exception {
		
		RegularUser loggedUser = regularUserRepository.findByEmail(name);
		
		List<Post> posts = new ArrayList();
		List<Following> followees = followingRepository.findAllByFollowerId(loggedUser.getId());
		
		if (followees.isEmpty()) {
			throw new UserNotFoundException("No followees found in the database");
		}
		
		followees.stream()
			.map(followee -> posts.addAll(followee.getFollowee().getPosts()));
		
//		posts.addAll(followees.stream()
//			.forEach(followee -> followee.getFollowee().getPosts()));

		
		Pageable firstPageWithTwoElements = PageRequest.of(0, 3);
		Page<Post> pagePosts = postRepository.findAll(firstPageWithTwoElements);
		
		List<PostDTO> pagePostsDTO = new ArrayList<>();
		
		for (Post post : pagePosts) {
			PostDTO postDTO = new PostDTO(post);
			pagePostsDTO.add(postDTO);
		}
		
		return pagePostsDTO;

	}

	public List<PostDTO> searchByTitle(String title) throws Exception {
		
		if (title == "") {
			throw new TitleNullException("Please enter title");
		}
		
		List<Post> allPosts = postRepository.findAll();

		if (allPosts.isEmpty()) {
			throw new PostNotFoundException("Posts not found");
		}
		
		List<PostDTO> filteredPosts = new ArrayList<>();
		
		for (Post post : allPosts) {
			PostDTO postDTO = new PostDTO(post);
			if (post.getTitle().toLowerCase().contains(title.toLowerCase())){
				filteredPosts.add(postDTO);
			}
		}
		
		//paginacija na search
		
		return filteredPosts;
	}
	
	public PostDTO create(PostDTO newPost, String name) throws Exception {
		 
		RegularUser loggedUser = regularUserRepository.findByEmail(name);
		
		if(!loggedUser.getRole().equals("ROLE_REGULAR_USER")) {
			throw new UnauthorizedUserException("User is not authorized to update this user");
		}
		
		Post post = new Post();
		post.setTitle(newPost.getTitle());
		post.setContent(newPost.getContent());
		post.setRegularUser(loggedUser);
		postRepository.save(post);
		
		return new PostDTO(post);
	}

	public PostDTO addComment(Integer id, CommentDTO newComment, String name) throws Exception {
		
		RegularUser loggedUser = regularUserRepository.findByEmail(name);
		Optional<Post> post = postRepository.findById(id);
		
		if(!loggedUser.getRole().equals("ROLE_REGULAR_USER")) {
			throw new UnauthorizedUserException("User is not authorized to update this user");
		}
		
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

	public PostDTO addLikeReaction(Integer id, String name) throws Exception {
		
		RegularUser loggedUser = regularUserRepository.findByEmail(name);
		Optional<Post> post = postRepository.findById(id);
		
		if(loggedUser.getRole().equals("ROLE_REGULAR_USER")) {
			throw new UnauthorizedUserException("User is not authorized to update this user");
		}
		
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
			
			return new PostDTO(post.get());
			
		}
		
		if (existingReaction.get().getReactionType() == null || existingReaction.get().getReactionType() == ReactionEnum.DISLIKE) {
			existingReaction.get().setReactionType(ReactionEnum.LIKE);

			reactionRepository.save(existingReaction.get());
			ArrayList<Reaction> postReactions = new ArrayList<>(post.get().getReactions());
			postReactions.add(existingReaction.get());
			postRepository.save(post.get());
			
			return new PostDTO(post.get());	
		}
		
		existingReaction.get().setReactionType(null);
		reactionRepository.save(existingReaction.get());
		postRepository.save(post.get());
		
		return new PostDTO(post.get());
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
					
				} else if (existingReaction.get().getReactionType() == ReactionEnum.DISLIKE){
					existingReaction.get().setReactionType(null);
					reactionRepository.save(existingReaction.get());
					postRepository.save(post.get());
				} 
				
				return new PostDTO(post.get());
			}
			
			throw new UnauthorizedUserException("User is not authorized to update this user");
		}

	
}
