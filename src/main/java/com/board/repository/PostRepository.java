package com.board.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.board.constant.Category;
import com.board.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>{
	List<Post> findByCategory(Category category);
}
