package com.example.demo.controllers;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.dto.RegularUserDTO;
import com.example.demo.entities.dto.UpdateUserDTO;
import com.example.demo.entities.dto.UserEmailDTO;
import com.example.demo.exceptions.CantFollowSelfException;
import com.example.demo.exceptions.FollowingExistsException;
import com.example.demo.exceptions.NonExistingEmailException;
import com.example.demo.exceptions.PasswordConfirmationException;
import com.example.demo.exceptions.UnauthorizedUserException;
import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.exceptions.UserWithEmailExistsException;
import com.example.demo.exceptions.UserWithUsernameExistsException;
import com.example.demo.services.RegularUserService;

@RestController
@RequestMapping(path = "api/v1/regular-users")
public class RegularUserController {
	
	private final RegularUserService regUserService;
	
	public RegularUserController(RegularUserService regUserService) {
		this.regUserService = regUserService;
	}

	@Secured("ROLE_ADMIN")
	@GetMapping
	public ResponseEntity<?> getAll() throws Exception {
		try {
			return new ResponseEntity<>(regUserService.getAll(), HttpStatus.OK);
		} catch (UserNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable Integer id) throws Exception {
		try {
			return new ResponseEntity<>(regUserService.getById(id), HttpStatus.OK);
		} catch (UserNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
	@GetMapping("/username/{username}")
	public ResponseEntity<?> searchByUsername(@PathVariable String username) throws Exception {
		try {
			return new ResponseEntity<>(regUserService.searchByUsername(username), HttpStatus.OK);
		} catch (UserNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
	@PostMapping
	public ResponseEntity<?> create(@Valid @RequestBody RegularUserDTO newUser) throws Exception {
		try {
			return new ResponseEntity<>(regUserService.create(newUser), HttpStatus.CREATED);
		} catch (UserWithEmailExistsException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (UserWithUsernameExistsException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (PasswordConfirmationException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_REGULAR_USER"})
	@PutMapping
	public ResponseEntity<?> update(@Valid @RequestBody UpdateUserDTO updatedUser, Authentication authentication) throws Exception {
		try {
			return new ResponseEntity<>(regUserService.update(updatedUser, authentication.getName()), HttpStatus.OK);
		} catch (UserWithEmailExistsException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (UserWithUsernameExistsException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (UnauthorizedUserException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		}
	}
	
	@Secured("ROLE_ADMIN")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteById(@PathVariable Integer id) throws Exception {
		try {
			return new ResponseEntity<>(regUserService.deleteById(id), HttpStatus.OK);
		} catch (UserNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
	@Secured("ROLE_REGULAR_USER")
	@PutMapping("/{id}")
	public ResponseEntity<?> followUserById(@PathVariable Integer id, Authentication authentication) throws Exception {
		try {
			return new ResponseEntity<>(regUserService.followUserById(id, authentication.getName()), HttpStatus.OK);
		} catch (UserNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		} catch (FollowingExistsException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		} catch (CantFollowSelfException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@PutMapping("/forgot-password")
	public ResponseEntity<?> forgotPassword(@RequestBody UserEmailDTO user) throws Exception {
		try {
			return new ResponseEntity<>(regUserService.forgotPassword(user), HttpStatus.OK);
		} catch (NonExistingEmailException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (UserNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
}
