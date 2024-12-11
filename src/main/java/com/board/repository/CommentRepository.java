package com.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.board.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {


}
