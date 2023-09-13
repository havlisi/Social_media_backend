package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.entities.Following;

public interface FollowingRepository extends CrudRepository<Following, Integer> {

	List<Integer> findAllByFollowerId(Integer id);
	
}
