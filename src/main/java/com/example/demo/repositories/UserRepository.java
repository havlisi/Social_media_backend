package com.example.demo.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.example.demo.entities.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, Integer> {

	public List<UserEntity> findAll();

	public Optional<UserEntity> findById(Integer id);

	public UserEntity findByEmail(String email);

	public UserEntity findByUsername(String username);
	
}
