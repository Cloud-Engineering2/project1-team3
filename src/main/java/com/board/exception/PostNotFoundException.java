package com.board.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class PostNotFoundException extends RuntimeException {
	private final Long pid;
	
	public PostNotFoundException(Long pid) {
		super(String.format("Post를 찾을 수 없습니다: Post ID - '%s'", pid));
		this.pid = pid;
	}
}
