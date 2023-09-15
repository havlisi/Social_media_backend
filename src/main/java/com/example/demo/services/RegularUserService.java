package com.example.demo.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.demo.entities.RegularUser;
import com.example.demo.entities.dto.RegularUserDTO;
import com.example.demo.entities.dto.UpdateUserDTO;
import com.example.demo.entities.dto.UserEmailDTO;
import com.example.demo.entities.dto.UserEntityDTO;

public interface RegularUserService {
	
	List<RegularUser> getAll() throws Exception;
	
	Optional<RegularUser> getById(Integer id) throws Exception;
	
	//prepvai na List
	ArrayList<RegularUserDTO> searchByUsername(String username) throws Exception;
	
	UserEntityDTO create(RegularUserDTO newUser) throws Exception;
	
	UpdateUserDTO update(UpdateUserDTO updatedUser, String name) throws Exception;
	
	String deleteById(Integer id) throws Exception;
	
	String followUserById(Integer id, String name) throws Exception;
	
	String forgotPassword(UserEmailDTO user) throws Exception;
}
