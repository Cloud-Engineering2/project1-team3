package com.board.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import com.board.constant.Category;
import com.board.entity.Post;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PostResponse {
	private Long id;
	private String title;
	private String content;
	private String summary;
	private Category category;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private String createdBy;
	private List<CommentResponse> comments;
	
	public PostResponse(Long id, String title, String content, String summary, Category category,
			LocalDateTime createdAt, LocalDateTime updatedAt, String createdBy, List<CommentResponse> comments) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.summary = summary;
        this.category = category;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdBy = createdBy;
        this.comments = comments;
	}
	
	public static PostResponse convertToDto(Post post) {
		List<CommentResponse> commentResponses = post.getPostComments().stream()
				.map(CommentResponse::convertToDto)
				.toList();
		return new PostResponse(
			post.getId(),
            post.getTitle(),
            post.getContent(),
            post.getSummary(),
            post.getCategory(),
            post.getCreatedAt(),
            post.getUpdatedAt(),
            post.getUser().getUsername(),
            commentResponses
		);
	}
}
