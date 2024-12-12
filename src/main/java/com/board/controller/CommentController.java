package com.board.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.board.dto.CommentDto;
import com.board.entity.Comment;
import com.board.service.CommentService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    // 댓글 작성
    @PostMapping

    public ResponseEntity<String> registerComment(
            @RequestParam Long postId,
            @RequestParam Long userId,
            @RequestBody CommentDto.CommentRequest content) {
        commentService.registerComment(postId, userId, content);
        return ResponseEntity.ok("Comment created successfully");
    }

    // 댓글 삭제
    @DeleteMapping("/{commentId}")

    public ResponseEntity<String> deleteComment(
            @PathVariable Long commentId,
            @RequestParam Long userId) {
        commentService.deleteComment(commentId, userId);
        return ResponseEntity.ok("Comment deleted successfully");
    }

    // 댓글 수정
    @PutMapping("/{commentId}")

    public ResponseEntity<String> updateComment(
            @PathVariable Long commentId,
            @RequestParam Long userId,
            @RequestBody CommentDto.CommentRequest newContent) {
        commentService.updateComment(commentId, userId, newContent);
        return ResponseEntity.ok("Comment updated successfully");
    }
}
