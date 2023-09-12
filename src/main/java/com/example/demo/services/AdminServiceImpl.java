package com.example.demo.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.example.demo.entities.AdminEntity;
import com.example.demo.entities.UserEntity;
import com.example.demo.entities.dto.UserEntityDTO;
import com.example.demo.repositories.AdminRepository;
import com.example.demo.repositories.UserRepository;

@Service
public class AdminServiceImpl implements AdminService {
	
	@Autowired
	AdminRepository adminRepository;
	
	@Autowired
	UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getAll() {
		List<AdminEntity> admins = (List<AdminEntity>) adminRepository.findAll();
		if (admins.isEmpty()) {
			return new ResponseEntity<>("No admin users found", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(admins, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "/{id}")
	public ResponseEntity<?> getById(@PathVariable Integer id) {
		Optional<AdminEntity> admin = adminRepository.findById(id);
		if (admin.isEmpty()) {
			return new ResponseEntity<>("Admin with that id not found", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(admin, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST) 
	public ResponseEntity<?> createAdmin (@RequestBody UserEntityDTO newUser) {
		
		AdminEntity admin = new AdminEntity();
		
		admin.setFirstName(newUser.getFirstName());
		admin.setLastName(newUser.getLastName());
		
		UserEntity existingUsername = userRepository.findByUsername(newUser.getUsername());
		
		if (existingUsername != null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		admin.setUsername(newUser.getUsername());
		
		UserEntity existingEmailUser = userRepository.findByEmail(newUser.getEmail());
		
		if (existingEmailUser != null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		admin.setEmail(newUser.getEmail());
		
		admin.setRole("ROLE_ADMIN");
		
		System.out.println(newUser.getFirstName());
		System.out.println(newUser.getLastName());
		System.out.println(newUser.getUsername());
		System.out.println(newUser.getEmail());
		System.out.println(newUser.getPassword());
		System.out.println(newUser.getConfirmedPassword());
		
		if (!newUser.getPassword().equals(newUser.getConfirmedPassword())) {
			return new ResponseEntity<>("Password must be same as confirmed password", HttpStatus.BAD_REQUEST);
		}
		
		admin.setPassword((passwordEncoder.encode(newUser.getPassword())));
		newUser.setConfirmedPassword((passwordEncoder.encode(newUser.getConfirmedPassword())));

		adminRepository.save(admin);
		
		return new ResponseEntity<UserEntityDTO>(new UserEntityDTO(admin, newUser.getConfirmedPassword()), HttpStatus.CREATED);
	}
	
	@RequestMapping(method = RequestMethod.PUT, path = "/{id}") 
	public ResponseEntity<?> updateRegularUser (@RequestBody UserEntityDTO updatedUser, @PathVariable Integer id) {
		
		Optional<AdminEntity> admin = adminRepository.findById(id);
		
		admin.get().setFirstName(updatedUser.getFirstName());
		admin.get().setLastName(updatedUser.getLastName());
		
		UserEntity existingUsername = userRepository.findByUsername(updatedUser.getUsername());
		
		if (existingUsername != null) {
			return new ResponseEntity<>("Username is taken!", HttpStatus.BAD_REQUEST);
		}
		
		admin.get().setUsername(updatedUser.getUsername());
		
		UserEntity existingEmailUser = userRepository.findByEmail(updatedUser.getEmail());
		
		if (existingEmailUser != null) {
			return new ResponseEntity<>("Email is taken!", HttpStatus.BAD_REQUEST);
		}
		
		admin.get().setEmail(updatedUser.getEmail());
		
		System.out.println(updatedUser.getFirstName());
		System.out.println(updatedUser.getLastName());
		System.out.println(updatedUser.getUsername());
		System.out.println(updatedUser.getEmail());
		System.out.println(updatedUser.getPassword());
		System.out.println(updatedUser.getConfirmedPassword());
		
		if (!updatedUser.getPassword().equals(updatedUser.getConfirmedPassword())) {
			return new ResponseEntity<>("Password must be same as confirmed password", HttpStatus.BAD_REQUEST);
		}
		
		admin.get().setPassword(updatedUser.getPassword());		
		updatedUser.setConfirmedPassword((passwordEncoder.encode(updatedUser.getConfirmedPassword())));

		adminRepository.save(admin.get());
		
		return new ResponseEntity<UserEntityDTO>(new UserEntityDTO(admin.get(),  updatedUser.getConfirmedPassword()), HttpStatus.CREATED);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
	public ResponseEntity<?> deleteById(@PathVariable Integer id) {
		Optional<AdminEntity> admin = adminRepository.findById(id);
		if (admin.isEmpty()) {
			return new ResponseEntity<>("Admin not found", HttpStatus.NOT_FOUND);
		}
		
		adminRepository.delete(admin.get());
		
		return new ResponseEntity<>("Successfully deleted admin", HttpStatus.OK);
	}

}
