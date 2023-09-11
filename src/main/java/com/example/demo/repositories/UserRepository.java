package com.example.demo.repositories;

import java.util.List;

import com.example.demo.entities.UserEntity;

public interface UserRepository {

	public List<UserEntity> findAll();

	public UserEntity findById(Integer id);
	
}
