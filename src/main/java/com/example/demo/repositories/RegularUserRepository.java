package com.example.demo.repositories;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import com.example.demo.entities.RegularUser;

public interface RegularUserRepository extends CrudRepository<RegularUser, Integer>{

	RegularUser findByEmail(String email);

	ArrayList<RegularUser> findByUsername(boolean b);
	
}
