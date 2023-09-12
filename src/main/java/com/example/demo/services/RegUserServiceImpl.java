package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
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
import com.example.demo.entities.RegularUserEntity;
import com.example.demo.entities.UserEntity;
import com.example.demo.entities.dto.RegularUserEntityDTO;
import com.example.demo.entities.dto.UserEmailDTO;
import com.example.demo.entities.dto.UserEntityDTO;
import com.example.demo.repositories.AdminRepository;
import com.example.demo.repositories.RegularUserRepository;
import com.example.demo.repositories.UserRepository;

@Service
public class RegUserServiceImpl implements RegularUserService {
	
	@Autowired
	RegularUserRepository regularUserRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	AdminRepository adminRepository;
	
	@Autowired
	EmailServiceImpl emailServiceImpl;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public ResponseEntity<?> getAll() {
		List<RegularUserEntity> users = (List<RegularUserEntity>) regularUserRepository.findAll();
		if (users.isEmpty()) {
			return new ResponseEntity<>("No users found", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(users, HttpStatus.OK);
	}
	
	public ResponseEntity<?> getById(@PathVariable Integer id) {
		Optional<RegularUserEntity> user = regularUserRepository.findById(id);
		if (user.isEmpty()) {
			return new ResponseEntity<>("User with that id not found", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST) 
	public ResponseEntity<?> createRegularUser (@RequestBody RegularUserEntityDTO newUser) {
		
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
		System.out.println(newUser.getConfirmedPassword());
		
		if (!newUser.getPassword().equals(newUser.getConfirmedPassword())) {
			return new ResponseEntity<>("Password must be same as confirmed password", HttpStatus.BAD_REQUEST);
		}
		
		user.setPassword((passwordEncoder.encode(newUser.getPassword())));
		newUser.setConfirmedPassword((passwordEncoder.encode(newUser.getConfirmedPassword())));
		
		userRepository.save(user);
		
		return new ResponseEntity<UserEntityDTO>(new UserEntityDTO(user, newUser.getConfirmedPassword()), HttpStatus.CREATED);
	}
	
	public ResponseEntity<?> updateRegularUser (@RequestBody RegularUserEntityDTO updatedUser, @PathVariable Integer id, Authentication authentication) {
		
		String email = authentication.getClass().getName();
		UserEntity loggedUser = userRepository.findByEmail(email);
		
		Optional<RegularUserEntity> user = regularUserRepository.findById(id);
		
		if(user.isEmpty()) {
			return new ResponseEntity<>("User not found in the database.", HttpStatus.NOT_FOUND);
		}
		
		if(loggedUser.getRole().equals("ROLE_REGULAR_USER")) {
			RegularUserEntity regularUser = (RegularUserEntity) loggedUser;
		}
		
		
		
		user.get().setFirstName(updatedUser.getFirstName());
		user.get().setLastName(updatedUser.getLastName());
		
		UserEntity existingUsername = userRepository.findByUsername(updatedUser.getUsername());
		
		if (existingUsername != null) {
			return new ResponseEntity<>("Username is taken!", HttpStatus.BAD_REQUEST);
		}
		
		user.get().setUsername(updatedUser.getUsername());
		
		//resiti menjanje email-a
		UserEntity existingEmailUser = userRepository.findByEmail(updatedUser.getEmail());
		
		if (existingEmailUser != null) {
			return new ResponseEntity<>("Email is taken!", HttpStatus.BAD_REQUEST);
		}
		
		user.get().setEmail(updatedUser.getEmail());
		
		System.out.println(updatedUser.getFirstName());
		System.out.println(updatedUser.getLastName());
		System.out.println(updatedUser.getUsername());
		System.out.println(updatedUser.getEmail());
		System.out.println(updatedUser.getPassword());
		System.out.println(updatedUser.getConfirmedPassword());
		
		if (!updatedUser.getPassword().equals(updatedUser.getConfirmedPassword())) {
			return new ResponseEntity<>("Password must be same as confirmed password", HttpStatus.BAD_REQUEST);
		}
		
		user.get().setPassword((passwordEncoder.encode(updatedUser.getPassword())));
		updatedUser.setConfirmedPassword((passwordEncoder.encode(updatedUser.getConfirmedPassword())));
		
		userRepository.save(user.get());
		
		return new ResponseEntity<UserEntityDTO>(new UserEntityDTO(user.get(), updatedUser.getConfirmedPassword()), HttpStatus.CREATED);
	}

	public ResponseEntity<?> deleteById(@PathVariable Integer id) {
		Optional<RegularUserEntity> user = regularUserRepository.findById(id);
		if (user.isEmpty()) {
			return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
		}
		
		regularUserRepository.delete(user.get());
		
		return new ResponseEntity<>("Successfully deleted user", HttpStatus.OK);
	}
	
	public ResponseEntity<?> forgotPassword(@RequestBody UserEmailDTO user) {
		
		UserEntity loggedUser = userRepository.findByEmail(user.getEmail());
		
		if (loggedUser == null) {
			return new ResponseEntity<>("Email doesn't exist.", HttpStatus.BAD_REQUEST);
		}
		
		if(loggedUser.getRole().equals("ROLE_ADMIN")) {
			AdminEntity admin = (AdminEntity) loggedUser;
			String newPass = "password123";
			admin.setPassword((passwordEncoder.encode(newPass)));
			adminRepository.save(admin);
			emailServiceImpl.sendNewMail("isidorahavlovic@gmail.com", newPass);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		else if(loggedUser.getRole().equals("ROLE_REGULAR_USER")) {
			RegularUserEntity regUser = (RegularUserEntity) loggedUser;
			String newPass = "password321";
			regUser.setPassword((passwordEncoder.encode(newPass)));
			userRepository.save(regUser);
			emailServiceImpl.sendNewMail("isidorahavlovic@gmail.com", newPass);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		
		return new ResponseEntity<>("User not found.", HttpStatus.NOT_FOUND);
	}

}
