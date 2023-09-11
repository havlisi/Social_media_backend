package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.UserEntity;
import com.example.demo.entities.dto.UserEntityDTO;
import com.example.demo.repositories.UserRepository;

@RestController
@RequestMapping(path = "api/v1/user")
public class UserController {
	
	@Autowired
	UserRepository userRepository;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getAll() {
		List<UserEntity> users = userRepository.findAll();
		return new ResponseEntity<>(users, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "/{id}")
	public ResponseEntity<?> getById(@PathVariable Integer id) {
		UserEntity user = userRepository.findById(id);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST) 
	public ResponseEntity<?> createUser (@RequestBody UserEntityDTO newUser) {
		UserEntity user = new UserEntity();
		
		user.setFirstName(newUser.getFirstName());
		user.setLastName(newUser.getLastName());
		user.setEmail(newUser.getEmail());
		user.setRole("ROLE_USER");
		user.setPassword(newUser.getFirstName());
		user.setFirstName(newUser.getFirstName());
		
		
		return new ResponseEntity<>(user, HttpStatus.CREATED);
	}
	
	
}
