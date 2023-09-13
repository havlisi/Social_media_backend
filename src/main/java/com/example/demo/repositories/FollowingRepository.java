package com.example.demo.repositories;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.example.demo.entities.Following;
import com.example.demo.entities.RegularUserEntity;

public interface FollowingRepository extends CrudRepository<Following, Integer> {

	List<RegularUserEntity> findAllByFollowerId(Integer id);
	
}
