package com.example.demo.services;

import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
import com.example.demo.entities.dto.UpdateUserEntityDTO;
import com.example.demo.entities.dto.UserEmailDTO;
import com.example.demo.entities.dto.UserEntityDTO;
import com.example.demo.exceptions.NonExistingEmailException;
import com.example.demo.exceptions.PasswordConfirmationException;
import com.example.demo.exceptions.UnauthorizedUserException;
import com.example.demo.exceptions.UserWithEmailExistsException;
import com.example.demo.exceptions.UserWithUsernameExistsException;
import com.example.demo.exceptions.UserNotFoundException;
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
	
	public ResponseEntity<?> getAll() throws Exception {
		List<RegularUserEntity> users = (List<RegularUserEntity>) regularUserRepository.findAll();
		if (users.isEmpty()) {
			throw new UserNotFoundException("No users found");
		}
		return new ResponseEntity<>(users, HttpStatus.OK);
	}
	
	public ResponseEntity<?> getById(@PathVariable Integer id) throws Exception {
		Optional<RegularUserEntity> user = regularUserRepository.findById(id);
		if (user.isEmpty()) {
			throw new UserNotFoundException("User with that id not found");
		}
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST) 
	public ResponseEntity<?> createRegularUser (@Valid @RequestBody RegularUserEntityDTO newUser) throws Exception {
		
		RegularUserEntity user = new RegularUserEntity();
		
		user.setFirstName(newUser.getFirstName());
		user.setLastName(newUser.getLastName());
		
		UserEntity existingUsername = userRepository.findByUsername(newUser.getUsername());
		
		if (existingUsername != null) {
			throw new UserWithUsernameExistsException("Username already exists");
		}
		
		user.setUsername(newUser.getUsername());
		
		UserEntity existingEmailUser = userRepository.findByEmail(newUser.getEmail());
		
		if (existingEmailUser != null) {
			throw new UserWithEmailExistsException("Email already exists");
		}
		
		user.setEmail(newUser.getEmail());
		
		user.setRole("ROLE_REGULAR_USER");
		
		if (!newUser.getPassword().equals(newUser.getConfirmedPassword())) {
			throw new PasswordConfirmationException("Password must be same as confirmed password");
		}
		
		user.setPassword((passwordEncoder.encode(newUser.getPassword())));
		newUser.setConfirmedPassword((passwordEncoder.encode(newUser.getConfirmedPassword())));
		
		userRepository.save(user);
		
		return new ResponseEntity<UserEntityDTO>(new UserEntityDTO(user, newUser.getConfirmedPassword()), HttpStatus.CREATED);
	}
	
	public ResponseEntity<?> updateUser (@Valid @RequestBody UpdateUserEntityDTO updatedUser, Authentication authentication) throws Exception {
		
		String email = authentication.getName();
		UserEntity loggedUser = userRepository.findByEmail(email);
		
		if(loggedUser.getRole().equals("ROLE_REGULAR_USER")) {
			RegularUserEntity regularUser = (RegularUserEntity) loggedUser;
			
			//dodati za svaki ikad field da li je null ako nije da se setuju podaci
			
			regularUser.setFirstName(updatedUser.getFirstName());
			regularUser.setLastName(updatedUser.getLastName());
			
			if (!regularUser.getUsername().equals(updatedUser.getUsername())) {
				UserEntity existingUsername = userRepository.findByUsername(updatedUser.getUsername());
				
				if (existingUsername != null) {
					throw new UserWithUsernameExistsException("Username already exists");
				}
			}
			
			regularUser.setUsername(updatedUser.getUsername());
			
			if (!regularUser.getEmail().equals(updatedUser.getEmail())) {
				UserEntity existingEmailUser = userRepository.findByEmail(updatedUser.getEmail());
				
				if (existingEmailUser != null) {
					throw new UserWithEmailExistsException("Email already exists");
				}
			}
			
			regularUser.setEmail(updatedUser.getEmail());
			
			userRepository.save(regularUser);
			
			return new ResponseEntity<UpdateUserEntityDTO>(new UpdateUserEntityDTO(regularUser), HttpStatus.OK);
		} else if(loggedUser.getRole().equals("ROLE_ADMIN")) {
			AdminEntity admin = (AdminEntity) loggedUser;
			
			admin.setFirstName(updatedUser.getFirstName());
			admin.setLastName(updatedUser.getLastName());
			
			if (!admin.getUsername().equals(updatedUser.getUsername())) {
				UserEntity existingUsername = userRepository.findByUsername(updatedUser.getUsername());
				
				if (existingUsername != null) {
					throw new UserWithUsernameExistsException("Username already exists");
				}
			}
			
			admin.setUsername(updatedUser.getUsername());
			
			if (!admin.getEmail().equals(updatedUser.getEmail())) {
				UserEntity existingEmailUser = userRepository.findByEmail(updatedUser.getEmail());
				
				if (existingEmailUser != null) {
					throw new UserWithEmailExistsException("Email already exists");
				}
			}
			
			admin.setEmail(updatedUser.getEmail());
			
			userRepository.save(admin);
			
			return new ResponseEntity<UpdateUserEntityDTO>(new UpdateUserEntityDTO(admin), HttpStatus.OK);
		}
		
		
		throw new UnauthorizedUserException("User is not authorized to update this user");
		
	}

	public ResponseEntity<?> deleteById(@PathVariable Integer id) throws Exception {
		Optional<RegularUserEntity> user = regularUserRepository.findById(id);
	
		if (user.isEmpty()) {
			throw new UserNotFoundException("User with that id not found");
		}
		
		regularUserRepository.delete(user.get());
		
		return new ResponseEntity<>("Successfully deleted user", HttpStatus.OK);
	}
	
	public ResponseEntity<?> forgotPassword(@RequestBody UserEmailDTO user) throws Exception {
		
		UserEntity loggedUser = userRepository.findByEmail(user.getEmail());
		
		if (loggedUser == null) {
			throw new NonExistingEmailException("Email doesn't exist.");
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
		
		throw new UserNotFoundException("User with that id not found");
	}

}
