package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.dto.CommentDTO;
import com.example.demo.entities.dto.PostDTO;
import com.example.demo.exceptions.PostNotFoundException;
import com.example.demo.exceptions.UnauthorizedUserException;
import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.implementation.PostServiceImpl;

@RestController
@RequestMapping(path = "api/v1/posts")
public class PostController {
	
	@Autowired
	UserRepository userRepository;

	@Autowired
	private PostServiceImpl postServiceImpl;
	
	@GetMapping
	public ResponseEntity<?> getAll() throws Exception {
		try {
			return new ResponseEntity<>(postServiceImpl.getAll(), HttpStatus.OK);
		} catch (UserNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
	@Secured("ROLE_REGULAR_USER")
	@GetMapping("/homepage")
	public ResponseEntity<?> getAllForHomePage(Authentication authentication) throws Exception {
		try {
			return new ResponseEntity<>(postServiceImpl.getAllForHomePage(authentication.getName()), HttpStatus.OK);
		} catch (PostNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		} catch (UserNotFoundException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		}
	}
	
	@Secured("ROLE_REGULAR_USER")
	@PostMapping
	public ResponseEntity<?> create(@RequestBody PostDTO newPost, Authentication authentication) throws Exception {
		try {
			return new ResponseEntity<>(postServiceImpl.create(newPost, authentication.getName()), HttpStatus.CREATED);
		} catch (UnauthorizedUserException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		}
	}
	
	@Secured("ROLE_REGULAR_USER")
	@PutMapping("/{id}")
	public ResponseEntity<?> addComment(@PathVariable Integer id, @RequestBody CommentDTO newComment, Authentication authentication) throws Exception {
		try {
			return new ResponseEntity<>(postServiceImpl.addComment(id, newComment, authentication.getName()), HttpStatus.CREATED);
		} catch (UnauthorizedUserException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		}
	}
	
//	@Secured("ROLE_REGULAR_USER") 
//	@PutMapping("/{id}")
//	public ResponseEntity<?> addReaction(@PathVariable Integer id, @RequestBody ReactionDTO newComment, Authentication authentication) throws Exception {
//		try {
//			return new ResponseEntity<>(postServiceImpl.addComment(id, newComment, authentication.getName()), HttpStatus.CREATED);
//		} catch (UnauthorizedUserException e) {
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
//		}
//	}

}
