package com.example.demo.repositories;

import org.springframework.data.repository.CrudRepository;
import com.example.demo.entities.RegularUser;

public interface RegularUserRepository extends CrudRepository<RegularUser, Integer>{

	RegularUser findByEmail(String email);
	
}
