package com.example.demo.repositories;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.example.demo.entities.PostEntity;
import com.example.demo.entities.RegularUserEntity;

public interface PostRepository extends CrudRepository<PostEntity, Integer> {

//	List<PostEntity> findAllByRegularUser(RegularUserEntity regularUserEntity);

}
