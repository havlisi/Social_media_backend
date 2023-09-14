package com.example.demo.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.example.demo.entities.User;

public interface UserRepository extends CrudRepository<User, Integer> {

	public List<User> findAll();

	public Optional<User> findById(Integer id);

	public User findByEmail(String email);

	public User findByUsername(String username);
	
}
