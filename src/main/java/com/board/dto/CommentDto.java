package com.board.dto;

import com.board.entity.Comment;

import lombok.Getter;

import java.time.LocalDateTime;

public class CommentDto {
    // 요청용 DTO
    public static class CommentRequest {
        private String content;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    // 응답용 DTO (필요한 경우 사용)
    @Getter
    public static class CommentResponse {
        private Long id;
        private String content;
        private Long postId;
        private Long userId;
        private LocalDateTime createdAt;

        public static CommentResponse from(Comment comment) {
            CommentResponse response = new CommentResponse();
            response.id = comment.getId();
            response.content = comment.getContent();
            response.postId = comment.getPost().getId();
            response.userId = comment.getUser().getUid();
            response.createdAt = comment.getCreatedAt();
            return response;
        }
        
    }
}