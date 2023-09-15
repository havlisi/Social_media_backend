package com.example.demo.services.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.demo.entities.Admin;
import com.example.demo.entities.Following;
import com.example.demo.entities.RegularUser;
import com.example.demo.entities.User;
import com.example.demo.entities.dto.RegularUserDTO;
import com.example.demo.entities.dto.UpdateUserDTO;
import com.example.demo.entities.dto.UserEmailDTO;
import com.example.demo.entities.dto.UserEntityDTO;
import com.example.demo.exceptions.CantFollowSelfException;
import com.example.demo.exceptions.FollowingExistsException;
import com.example.demo.exceptions.NonExistingEmailException;
import com.example.demo.exceptions.PasswordConfirmationException;
import com.example.demo.exceptions.UnauthorizedUserException;
import com.example.demo.exceptions.UserWithEmailExistsException;
import com.example.demo.exceptions.UserWithUsernameExistsException;
import com.example.demo.exceptions.UsernameNullException;
import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.repositories.AdminRepository;
import com.example.demo.repositories.FollowingRepository;
import com.example.demo.repositories.RegularUserRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.RegularUserService;

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
	
	@Autowired
	FollowingRepository followingRepository;
	
	public List<RegularUser> getAll() throws Exception {
		List<RegularUser> users = (List<RegularUser>) regularUserRepository.findAll();
		if (users.isEmpty()) {
			throw new UserNotFoundException("No users found");
		}
		return users;
	}
	
	public Optional<RegularUser> getById(Integer id) throws Exception {
		Optional<RegularUser> user = regularUserRepository.findById(id);
		if (user.isEmpty()) {
			throw new UserNotFoundException("User with that id not found");
		}
		return user;
	}
	

	public ArrayList<RegularUserDTO> searchByUsername(String username) throws Exception {
		
		if (username == "") {
			throw new UsernameNullException("Please enter username");
		}
		
		ArrayList<RegularUser> allUsers = (ArrayList<RegularUser>) regularUserRepository.findAll();

		if (allUsers.isEmpty()) {
			throw new UserNotFoundException("Users not found");
		}
		
		ArrayList<RegularUserDTO> filteredUsers = new ArrayList<>();
		
		for (RegularUser user : allUsers) {
			RegularUserDTO userDTO = new RegularUserDTO(user);
			if (user.getUsername().contains(username.toLowerCase())){
				filteredUsers.add(userDTO);
			}
		}
		return filteredUsers;
	}

	
	public UserEntityDTO create(RegularUserDTO newUser) throws Exception {
		
		RegularUser user = new RegularUser();
		
		user.setFirstName(newUser.getFirstName());
		user.setLastName(newUser.getLastName());
		
		User existingUsername = userRepository.findByUsername(newUser.getUsername());
		
		if (existingUsername != null)
			throw new UserWithUsernameExistsException("Username already exists");
		
		user.setUsername(newUser.getUsername());
		
		User existingEmailUser = userRepository.findByEmail(newUser.getEmail());
		
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
		
		return new UserEntityDTO(user, newUser.getConfirmedPassword());
	}
	
	public UpdateUserDTO update(UpdateUserDTO updatedUser, String name) throws Exception {
		
		User loggedUser = userRepository.findByEmail(name);
		
		RegularUser regularUser = (RegularUser) loggedUser;
		Admin admin = (Admin) loggedUser;
		
		if(loggedUser.getRole().equals("ROLE_REGULAR_USER")) {
			if (updatedUser.getFirstName() != null)
				regularUser.setFirstName(updatedUser.getFirstName());
			
			if (updatedUser.getLastName() != null)
				regularUser.setLastName(updatedUser.getLastName());
			
			if (updatedUser.getUsername() != null) {
				if (!regularUser.getUsername().equals(updatedUser.getUsername())) {
					User existingUsername = userRepository.findByUsername(updatedUser.getUsername());
					
					if (existingUsername != null) {
						throw new UserWithUsernameExistsException("Username already exists");
					}
				}
				
				regularUser.setUsername(updatedUser.getUsername());
			}
			
			if (updatedUser.getEmail() != null) {
				if (!regularUser.getEmail().equals(updatedUser.getEmail())) {
					User existingEmailUser = userRepository.findByEmail(updatedUser.getEmail());
					
					if (existingEmailUser != null) {
						throw new UserWithEmailExistsException("Email already exists");
					}
				}
				
				regularUser.setEmail(updatedUser.getEmail());
			}
			
			userRepository.save(regularUser);
			
			return new UpdateUserDTO(regularUser);
		} else if(loggedUser.getRole().equals("ROLE_ADMIN")) {

			if (updatedUser.getFirstName() != null) {
				admin.setFirstName(updatedUser.getFirstName());
			}
			
			if (updatedUser.getLastName() != null) {
				admin.setLastName(updatedUser.getLastName());
			}
			
			if (updatedUser.getUsername() != null) {
				if (!admin.getUsername().equals(updatedUser.getUsername())) {
					User existingUsername = userRepository.findByUsername(updatedUser.getUsername());
					
					if (existingUsername != null) {
						throw new UserWithUsernameExistsException("Username already exists");
					}
				}
				admin.setUsername(updatedUser.getUsername());
			}
			
			
			if (updatedUser.getEmail() != null) {
				if (!admin.getEmail().equals(updatedUser.getEmail())) {
					User existingEmailUser = userRepository.findByEmail(updatedUser.getEmail());
					
					if (existingEmailUser != null) {
						throw new UserWithEmailExistsException("Email already exists");
					}
				}
				admin.setEmail(updatedUser.getEmail());
			}
			
			
			userRepository.save(admin);
			
			return new UpdateUserDTO(admin);
		}
		
		
		throw new UnauthorizedUserException("User is not authorized to update this user");
		
	}

	public String deleteById(Integer id) throws Exception {
		Optional<RegularUser> user = regularUserRepository.findById(id);
	
		if (user.isEmpty()) {
			throw new UserNotFoundException("User with that id not found");
		}
		
		regularUserRepository.delete(user.get());
		
		return "Successfully deleted user";
	}

	public String followUserById(Integer id, String name) throws Exception {
		
		RegularUser loggedUser = regularUserRepository.findByEmail(name);
		
		Optional<RegularUser> followedUser = regularUserRepository.findById(id);
		
		if (followedUser.isEmpty()) {
			throw new UserNotFoundException("User with that id not found");
		}
		
		Optional<Following> followExist = followingRepository.findByFollowerAndFollowee(loggedUser, followedUser.get());
		
		if (followExist.isPresent()) {
			throw new FollowingExistsException("User is already following this user");
		}
		
		if (loggedUser.getId() == followedUser.get().getId()) {
			throw new CantFollowSelfException("User can't follow himself");
		}
		
		followingRepository.save(new Following(loggedUser, followedUser.get()));
		return ("Successfully followed " + followedUser.get().getUsername());
		
	}
	
	public String forgotPassword(UserEmailDTO user) throws Exception {
		
		User loggedUser = userRepository.findByEmail(user.getEmail());
		
		if (loggedUser == null) {
			throw new NonExistingEmailException("Email doesn't exist.");
		}
		
		if(loggedUser.getRole().equals("ROLE_ADMIN")) {
			Admin admin = (Admin) loggedUser;
			String newPass = "password123";
			admin.setPassword((passwordEncoder.encode(newPass)));
			adminRepository.save(admin);
			emailServiceImpl.sendNewMail("isidorahavlovic@gmail.com", newPass);
			return "New password has been sent to admin";
		}
		else if(loggedUser.getRole().equals("ROLE_REGULAR_USER")) {
			RegularUser regUser = (RegularUser) loggedUser;
			String newPass = "password321";
			regUser.setPassword((passwordEncoder.encode(newPass)));
			userRepository.save(regUser);
			emailServiceImpl.sendNewMail("isidorahavlovic@gmail.com", newPass);
			return "New password has been sent to regular user";
		}
		
		throw new UserNotFoundException("User with that id not found");
	}

}
