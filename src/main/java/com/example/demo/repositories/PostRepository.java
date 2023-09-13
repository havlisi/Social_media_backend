package com.example.demo.repositories;

import org.springframework.data.repository.CrudRepository;
import com.example.demo.entities.PostEntity;

public interface PostRepository extends CrudRepository<PostEntity, Integer> {

//	List<PostEntity> findAllByRegularUser(RegularUserEntity regularUserEntity);

}
