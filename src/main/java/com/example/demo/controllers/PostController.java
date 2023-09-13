package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.entities.UserEntity;
import com.example.demo.exceptions.UnauthorizedUserException;
import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.PostServiceImpl;

@RestController
@RequestMapping(path = "api/v1/post")
public class PostController {
	
	@Autowired
	UserRepository userRepository;

	@Autowired
	private PostServiceImpl postServiceImpl;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getAll() throws Exception {
		try {
			return new ResponseEntity<>(postServiceImpl.getAll(), HttpStatus.OK);
		} catch (UserNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
//	@Secured("ROLE_REGULAR_USER")
//	@RequestMapping(method = RequestMethod.GET)
//	public ResponseEntity<?> getAllByFollowingUser(Authentication authentication) throws Exception {
//		try {
//			return new ResponseEntity<>(postServiceImpl.getAllByFollowingUser(authentication), HttpStatus.OK);
//		} catch (UserNotFoundException e) {
//			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
//		} catch (UnauthorizedUserException e) {
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
//		}
//	}
	
	//resi do kraja post
	@Secured("ROLE_REGULAR_USER")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> createPost (Authentication authentication) {
		
		String email = authentication.getClass().getName();
		UserEntity loggedUser = userRepository.findByEmail(email);
		
		if(loggedUser.getRole().equals("ROLE_REGULAR_USER")) {
			
		}
		
		return null;
	}

}
