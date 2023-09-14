package com.example.demo.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.entities.Post;
import com.example.demo.entities.Reaction;
import com.example.demo.entities.RegularUser;

public interface ReactionRepository extends CrudRepository<Reaction, Integer>{

	Optional<Reaction> findByPostAndRegularUser(Post post, RegularUser regularUser);

}
