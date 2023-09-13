package com.example.demo.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.entities.dto.RegularUserEntityDTO;
import com.example.demo.entities.dto.UpdateUserEntityDTO;
import com.example.demo.entities.dto.UserEmailDTO;
import com.example.demo.exceptions.NonExistingEmailException;
import com.example.demo.exceptions.PasswordConfirmationException;
import com.example.demo.exceptions.UnauthorizedUserException;
import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.exceptions.UserWithEmailExistsException;
import com.example.demo.exceptions.UserWithUsernameExistsException;
import com.example.demo.services.RegUserServiceImpl;

@RestController
@RequestMapping(path = "api/v1/regular-user")
public class RegularUserController {
	
	@Autowired
	private RegUserServiceImpl regUserServiceImpl;
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getAll() throws Exception {
		try {
			return new ResponseEntity<>(regUserServiceImpl.getAll(), HttpStatus.OK);
		} catch (UserNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.GET, path = "/{id}")
	public ResponseEntity<?> getById(@PathVariable Integer id) throws Exception {
		try {
			return new ResponseEntity<>(regUserServiceImpl.getById(id), HttpStatus.OK);
		} catch (UserNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
	@RequestMapping(method = RequestMethod.POST) 
	public ResponseEntity<?> createRegularUser (@Valid @RequestBody RegularUserEntityDTO newUser) throws Exception {
		try {
			return new ResponseEntity<>(regUserServiceImpl.createRegularUser(newUser), HttpStatus.CREATED);
		} catch (UserWithEmailExistsException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (UserWithUsernameExistsException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (PasswordConfirmationException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_REGULAR_USER"})
	@RequestMapping(method = RequestMethod.PUT) 
	public ResponseEntity<?> updateUser (@Valid @RequestBody UpdateUserEntityDTO updatedUser, Authentication authentication) throws Exception {
		try {
			return new ResponseEntity<>(regUserServiceImpl.updateUser(updatedUser, authentication), HttpStatus.OK);
		} catch (UserWithEmailExistsException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (UserWithUsernameExistsException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (UnauthorizedUserException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		}
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
	public ResponseEntity<?> deleteById(@PathVariable Integer id) throws Exception {
		try {
			return new ResponseEntity<>(regUserServiceImpl.deleteById(id), HttpStatus.OK);
		} catch (UserNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
	@Secured("ROLE_REGULAR_USER")
	@RequestMapping(method = RequestMethod.PUT, path = "/{id}")
	public ResponseEntity<?> followUserById(@PathVariable Integer id, Authentication authentication) throws Exception {
		try {
			return new ResponseEntity<>(regUserServiceImpl.followUserById(id, authentication), HttpStatus.OK);
		} catch (UserNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
	@RequestMapping(method = RequestMethod.PUT, path = "/forgot-password")
	public ResponseEntity<?> forgotPassword(@RequestBody UserEmailDTO user) throws Exception {
		try {
			return new ResponseEntity<>(regUserServiceImpl.forgotPassword(user), HttpStatus.OK);
		} catch (NonExistingEmailException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (UserNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
}
