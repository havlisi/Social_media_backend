package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.entities.dto.UserEntityDTO;
import com.example.demo.services.implementation.AdminServiceImpl;

@RestController
@RequestMapping(path = "api/v1/admins")
public class AdminController {
	
	@Autowired
	private AdminServiceImpl adminServiceImpl;

	@Secured("ROLE_ADMIN")
	@GetMapping
	public ResponseEntity<?> getAll() {
		return adminServiceImpl.getAll();
	}
	
	@Secured("ROLE_ADMIN")
	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable Integer id) {
		return adminServiceImpl.getById(id);
	}
	
	@PostMapping
	public ResponseEntity<?> create (@RequestBody UserEntityDTO newUser) {
		return adminServiceImpl.create(newUser);
	}
	
	@Secured("ROLE_ADMIN")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteById(@PathVariable Integer id) {
		return adminServiceImpl.deleteById(id);	
	}
}
