package com.example.demo.controllers;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.entities.dto.RegularUserEntityDTO;
import com.example.demo.services.RegUserServiceImpl;

@RestController
@RequestMapping(path = "api/v1/regularUser")
public class RegularUserController {
	
	@Autowired
	private RegUserServiceImpl regUserServiceImpl;
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getAll() {
		return regUserServiceImpl.getAll();
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.GET, path = "/{id}")
	public ResponseEntity<?> getById(@PathVariable Integer id) {
		return regUserServiceImpl.getById(id);
	}
	
	@RequestMapping(method = RequestMethod.POST) 
	public ResponseEntity<?> createRegularUser (@RequestBody RegularUserEntityDTO newUser) {
		return regUserServiceImpl.createRegularUser(newUser);
	}
	
	@RequestMapping(method = RequestMethod.PUT, path = "/{id}") 
	public ResponseEntity<?> updateRegularUser (@RequestBody RegularUserEntityDTO updatedUser, @PathVariable Integer id) {
		return regUserServiceImpl.updateRegularUser(updatedUser, id);
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
	public ResponseEntity<?> deleteById(@PathVariable Integer id) {
		return regUserServiceImpl.deleteById(id);
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_REGULAR_USER"})
	public ResponseEntity<?> forgotPassword(String userEmail) {
		return regUserServiceImpl.forgotPassword(userEmail);
	}
}
