package com.board.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.board.dto.request.PostRequest;
import com.board.dto.response.PostResponse;
import com.board.dto.response.PostsResponse;
import com.board.service.PostService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import com.board.dto.security.PrincipalDetails;

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
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		Object principal = authentication.getPrincipal();
		
		Long currentUid = null;
		if (principal instanceof PrincipalDetails) {
			currentUid = ((PrincipalDetails) principal).getUid();
		}
		
		PostResponse post = postService.createPost(request, currentUid);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(post);
	}
	
	@PutMapping("/{pid}")
	public ResponseEntity<PostResponse> updatePost(
			@PathVariable Long pid,
			@Valid @RequestBody PostRequest request
	) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		Object principal = authentication.getPrincipal();
		
		Long currentUid = null;
		if (principal instanceof PrincipalDetails) {
			currentUid = ((PrincipalDetails) principal).getUid();
		}
	    
		PostResponse post = postService.updatePost(pid, request, currentUid);
		return ResponseEntity.status(HttpStatus.OK).body(post);
	}

	@DeleteMapping("/{pid}")
	public ResponseEntity<String> deletePost(@PathVariable Long pid) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		Object principal = authentication.getPrincipal();
		
		Long currentUid = null;
		if (principal instanceof PrincipalDetails) {
			currentUid = ((PrincipalDetails) principal).getUid();
		}
		
		postService.deletePost(pid, currentUid);
		return ResponseEntity.ok("Successfully Deleted: Post ID - " + pid);
	}
}
