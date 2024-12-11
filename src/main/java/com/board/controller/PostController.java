package com.board.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.util.List;

import com.board.dto.request.PostRequest;
import com.board.dto.response.PostResponse;
import com.board.service.PostService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
	private final PostService postService;
	
	@GetMapping()
	public ResponseEntity<List<PostResponse>> getPosts() {
		List<PostResponse> posts = postService.getPosts();
		return ResponseEntity.ok(posts);
	}
	
	@PostMapping()
	public ResponseEntity<PostResponse> createPost(@RequestBody PostRequest request) {
		PostResponse post = postService.createPost(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(post);
	}
}
