package com.board.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.util.List;

import com.board.dto.request.PostRequest;
import com.board.dto.response.PostResponse;
import com.board.dto.response.PostsResponse;
import com.board.service.PostService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
	private final PostService postService;
	
	@GetMapping()
	public ResponseEntity<List<PostsResponse>> getPosts() {
		List<PostsResponse> posts = postService.getPosts();
		return ResponseEntity.ok(posts);
	}
	
	@GetMapping("/{pid}")
	public ResponseEntity<PostResponse> getPost(@PathVariable Long pid) {
		PostResponse post = postService.getPost(pid);
		return ResponseEntity.ok(post);
	}
	
	@PostMapping()
	public ResponseEntity<PostResponse> createPost(@Valid @RequestBody PostRequest request) {
		PostResponse post = postService.createPost(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(post);
	}
	
	@PutMapping("/{pid}")
	public ResponseEntity<PostResponse> updatePost(
			@PathVariable Long pid,
			@Valid @RequestBody PostRequest request
	) {
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//	    Long currentUid = authentication.getUid();
	    
		PostResponse post = postService.updatePost(pid, request);
		return ResponseEntity.status(HttpStatus.OK).body(post);
	}

	@DeleteMapping("/{pid}")
	public ResponseEntity<String> deletePost(@PathVariable Long pid) {
		postService.deletePost(pid);
		return ResponseEntity.ok("Successfully Deleted: Post ID - " + pid);
	}
}
