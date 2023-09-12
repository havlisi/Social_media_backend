package com.example.demo.controllers;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.entities.UserEntity;
import com.example.demo.repositories.UserRepository;

@RestController
@RequestMapping(path = "api/v1/post")
public class PostController {
	
	@Autowired
	UserRepository userRepository;
	
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
