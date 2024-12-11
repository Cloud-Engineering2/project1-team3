package com.board.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class PostRequest {
	@NotBlank(message = "Title is required")
    private String title;
	
	@NotBlank(message = "Content is required")
    private String content;
	
	@NotBlank(message = "Category is required")
    private String category;
}
