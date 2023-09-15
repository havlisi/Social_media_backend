package com.example.demo.services;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.entities.Post;
import com.example.demo.entities.dto.CommentDTO;
import com.example.demo.entities.dto.PostDTO;

public interface PostService {
	
	List<Post> getAll() throws Exception;
	
	List<PostDTO> getAllForHomePage(String name) throws Exception;
	
	List<PostDTO> searchByTitle(String title) throws Exception;
	
	PostDTO create(PostDTO newPost, String name) throws Exception;
	
	PostDTO addComment(Integer id, CommentDTO newComment, String name) throws Exception;
	
	PostDTO addLikeReaction(Integer id, String name) throws Exception;
	
	PostDTO addDislikeReaction(Integer id, String name) throws Exception;

}
