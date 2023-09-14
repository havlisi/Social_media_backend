package com.example.demo.repositories;

import org.springframework.data.repository.CrudRepository;
import com.example.demo.entities.Post;

public interface PostRepository extends CrudRepository<Post, Integer> {

//	List<PostEntity> findAllByRegularUser(RegularUserEntity regularUserEntity);

}
