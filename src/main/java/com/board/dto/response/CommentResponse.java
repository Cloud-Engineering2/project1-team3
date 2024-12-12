package com.board.dto.response;

import java.time.LocalDateTime;

import com.board.entity.Comment;

import lombok.Getter;

@Getter
public class CommentResponse {
    private Long id;
    private String content;
    private String createdBy;
    private LocalDateTime createdAt;

    public CommentResponse(Long id, String content, String createdBy, LocalDateTime createdAt) {
        this.id = id;
        this.content = content;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
    }

    public static CommentResponse convertToDto(Comment comment) {
        return new CommentResponse(
            comment.getId(),
            comment.getContent(),
            comment.getUser().getUsername(),
            comment.getCreatedAt()
        );
    }
}
