package com.board.dto.response;

import java.time.LocalDateTime;

import com.board.constant.Category;
import com.board.entity.Post;

import lombok.Getter;
import lombok.ToString;

/*
 * Post 전체 조회에 대한 Response로
 * Comment를 포함하지 않고 Comment의 개수를 포함한다.
 */
@Getter
@ToString
public class PostsResponse {
	private Long id;
	private String title;
	private Category category;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private String createdBy;
	private int commentCount;
	
	public PostsResponse(Long id, String title, Category category,LocalDateTime createdAt,
			LocalDateTime updatedAt, String createdBy, int commentCount) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdBy = createdBy;
        this.commentCount = commentCount;
	}
	
	public static PostsResponse convertToDto(Post post) {
		return new PostsResponse(
			post.getId(),
            post.getTitle(),
            post.getCategory(),
            post.getCreatedAt(),
            post.getUpdatedAt(),
            post.getUser().getUsername(),
            post.getPostComments().size()
		);
	}
}
