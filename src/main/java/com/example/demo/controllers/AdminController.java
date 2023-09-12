package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.entities.dto.UserEntityDTO;
import com.example.demo.services.AdminServiceImpl;

@RestController
@RequestMapping(path = "api/v1/admin")
public class AdminController {
	
	@Autowired
	private AdminServiceImpl adminServiceImpl;

	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getAll() {
		return adminServiceImpl.getAll();
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.GET, path = "/{id}")
	public ResponseEntity<?> getById(@PathVariable Integer id) {
		return adminServiceImpl.getById(id);
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.POST) 
	public ResponseEntity<?> createAdmin (@RequestBody UserEntityDTO newUser) {
		return adminServiceImpl.createAdmin(newUser);
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.PUT, path = "/{id}") 
	public ResponseEntity<?> updateRegularUser (@RequestBody UserEntityDTO updatedUser, @PathVariable Integer id) {
		return adminServiceImpl.updateRegularUser(updatedUser, id);	
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
	public ResponseEntity<?> deleteById(@PathVariable Integer id) {
		return adminServiceImpl.deleteById(id);	
	}
}
