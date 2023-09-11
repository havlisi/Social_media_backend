package com.example.demo.controllers;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.entities.RegularUserEntity;
import com.example.demo.entities.UserEntity;
import com.example.demo.entities.dto.UserEntityDTO;
import com.example.demo.repositories.RegularUserRepository;
import com.example.demo.repositories.UserRepository;

@RestController
@RequestMapping(path = "api/v1/regularUser")
public class RegularUserController {
	
	@Autowired
	RegularUserRepository regularUserRepository;
	
	@Autowired
	UserRepository userRepository;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getAll() {
		List<RegularUserEntity> users = (List<RegularUserEntity>) regularUserRepository.findAll();
		if (users.isEmpty()) {
			return new ResponseEntity<>("No users found", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(users, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "/{id}")
	public ResponseEntity<?> getById(@PathVariable Integer id) {
		Optional<RegularUserEntity> user = regularUserRepository.findById(id);
		if (user.isEmpty()) {
			return new ResponseEntity<>("User with that id not found", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST) 
	public ResponseEntity<?> createRegularUser (@RequestBody UserEntityDTO newUser) {
		
		RegularUserEntity user = new RegularUserEntity();
		
		user.setFirstName(newUser.getFirstName());
		user.setLastName(newUser.getLastName());
		
		UserEntity existingUsername = userRepository.findByUsername(newUser.getUsername());
		
		if (existingUsername != null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		user.setUsername(newUser.getUsername());
		
		UserEntity existingEmailUser = userRepository.findByEmail(newUser.getEmail());
		
		if (existingEmailUser != null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		user.setEmail(newUser.getEmail());
		
		user.setRole("ROLE_REGULAR_USER");
		
		System.out.println(newUser.getFirstName());
		System.out.println(newUser.getLastName());
		System.out.println(newUser.getUsername());
		System.out.println(newUser.getEmail());
		System.out.println(newUser.getPassword());
		System.out.println(newUser.getConfirmed_password());
		
		if (!newUser.getPassword().equals(newUser.getConfirmed_password())) {
			return new ResponseEntity<>("Password must be same as confirmed password", HttpStatus.BAD_REQUEST);
		}
		
		user.setPassword(newUser.getPassword());
		
		//dodati posts
		
		userRepository.save(user);
		
		return new ResponseEntity<UserEntityDTO>(new UserEntityDTO(user), HttpStatus.CREATED);
	}
	
	@RequestMapping(method = RequestMethod.PUT, path = "/{id}") 
	public ResponseEntity<?> updateRegularUser (@RequestBody UserEntityDTO updatedUser, @PathVariable Integer id) {
		
		Optional<RegularUserEntity> user = regularUserRepository.findById(id);
		
		user.get().setFirstName(updatedUser.getFirstName());
		user.get().setLastName(updatedUser.getLastName());
		
		UserEntity existingUsername = userRepository.findByUsername(updatedUser.getUsername());
		
		if (existingUsername != null) {
			return new ResponseEntity<>("Username is taken!", HttpStatus.BAD_REQUEST);
		}
		
		user.get().setUsername(updatedUser.getUsername());
		
		UserEntity existingEmailUser = userRepository.findByEmail(updatedUser.getEmail());
		
		if (existingEmailUser != null) {
			return new ResponseEntity<>("Email is taken!", HttpStatus.BAD_REQUEST);
		}
		
		user.get().setEmail(updatedUser.getEmail());
		
		user.get().setRole("ROLE_REGULAR_USER");
		
		System.out.println(updatedUser.getFirstName());
		System.out.println(updatedUser.getLastName());
		System.out.println(updatedUser.getUsername());
		System.out.println(updatedUser.getEmail());
		System.out.println(updatedUser.getPassword());
		System.out.println(updatedUser.getConfirmed_password());
		
		if (!updatedUser.getPassword().equals(updatedUser.getConfirmed_password())) {
			return new ResponseEntity<>("Password must be same as confirmed password", HttpStatus.BAD_REQUEST);
		}
		
		user.get().setPassword(updatedUser.getPassword());
		
		//dodati posts
		
		userRepository.save(user.get());
		
		return new ResponseEntity<UserEntityDTO>(new UserEntityDTO(user.get()), HttpStatus.CREATED);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
	public ResponseEntity<?> deleteById(@PathVariable Integer id) {
		Optional<RegularUserEntity> user = regularUserRepository.findById(id);
		if (user.isEmpty()) {
			return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
		}
		
		regularUserRepository.delete(user.get());
		
		return new ResponseEntity<>("Successfully deleted user", HttpStatus.OK);
	}
}
