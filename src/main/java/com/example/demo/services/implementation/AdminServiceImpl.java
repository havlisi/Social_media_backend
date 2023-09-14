package com.example.demo.services.implementation;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import com.example.demo.entities.Admin;
import com.example.demo.entities.User;
import com.example.demo.entities.dto.UserEntityDTO;
import com.example.demo.repositories.AdminRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.AdminService;

@Service
public class AdminServiceImpl implements AdminService {
	
	@Autowired
	AdminRepository adminRepository;
	
	@Autowired
	UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public ResponseEntity<?> getAll() {
		List<Admin> admins = (List<Admin>) adminRepository.findAll();
		if (admins.isEmpty()) {
			return new ResponseEntity<>("No admin users found", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(admins, HttpStatus.OK);
	}
	
	public ResponseEntity<?> getById(@PathVariable Integer id) {
		Optional<Admin> admin = adminRepository.findById(id);
		if (admin.isEmpty()) {
			return new ResponseEntity<>("Admin with that id not found", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(admin, HttpStatus.OK);
	}
	
	public ResponseEntity<?> create (@RequestBody UserEntityDTO newUser) {
		
		Admin admin = new Admin();
		
		admin.setFirstName(newUser.getFirstName());
		admin.setLastName(newUser.getLastName());
		
		User existingUsername = userRepository.findByUsername(newUser.getUsername());
		
		if (existingUsername != null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		admin.setUsername(newUser.getUsername());
		
		User existingEmailUser = userRepository.findByEmail(newUser.getEmail());
		
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
	
	public ResponseEntity<?> deleteById(@PathVariable Integer id) {
		Optional<Admin> admin = adminRepository.findById(id);
		if (admin.isEmpty()) {
			return new ResponseEntity<>("Admin not found", HttpStatus.NOT_FOUND);
		}
		
		adminRepository.delete(admin.get());
		
		return new ResponseEntity<>("Successfully deleted admin", HttpStatus.OK);
	}

}
