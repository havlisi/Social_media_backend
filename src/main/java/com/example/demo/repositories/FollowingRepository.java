package com.example.demo.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import com.example.demo.entities.Following;
import com.example.demo.entities.RegularUser;

public interface FollowingRepository extends CrudRepository<Following, Integer> {

	List<Following> findAllByFollowerId(Integer id);

	Optional<Following> findByFollowerAndFollowee(RegularUser loggedUser, RegularUser followedUser);
	
}
