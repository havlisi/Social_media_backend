package com.example.demo.controllers;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.entities.User;
import com.example.demo.exceptions.UnauthorizedUserException;
import com.example.demo.services.implementation.UserServiceImpl;

@RestController
@RequestMapping(path = "api/v1/users")
public class UserController {
	
	@Autowired
	private UserServiceImpl userServiceImpl;
	
	
	public String getJWTToken(User userEntity) {
		return userServiceImpl.getJWTToken(userEntity);
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody Map<String, String> korisnikLogger) throws Exception {
		try {
			return new ResponseEntity<>(userServiceImpl.login(korisnikLogger), HttpStatus.OK);
		} catch (UnauthorizedUserException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		}
	}
	
}
