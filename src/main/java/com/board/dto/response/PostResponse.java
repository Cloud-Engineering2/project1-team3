package com.board.dto.response;

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
	
	public PostResponse(Long id, String title, String content, String summary, Category category) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.summary = summary;
        this.category = category;
	}
	
	public static PostResponse convertToDto(Post post) {
		return new PostResponse(
			post.getId(),
            post.getTitle(),
            post.getContent(),
            post.getSummary(),
            post.getCategory()
		);
	}
}
