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

import com.board.common.SuccessResponse;
import com.board.common.base.BaseResponse;
import com.board.dto.request.PostRequest;
import com.board.dto.response.PostResponse;
import com.board.dto.response.PostsResponse;
import com.board.dto.security.PrincipalDetails;
import com.board.service.PostService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
	private final PostService postService;
	
	@GetMapping()
	public ResponseEntity<SuccessResponse<List<PostsResponse>>> getPosts() {
		List<PostsResponse> posts = postService.getPosts();
		return new ResponseEntity<>(
				new SuccessResponse<>(HttpStatus.OK.value(), "게시글 리스트가 조회되었습니다.", posts),
				HttpStatus.OK
		);
	}
	
	@GetMapping("/{pid}")
	public ResponseEntity<SuccessResponse<PostResponse>> getPost(@PathVariable Long pid) {
		PostResponse post = postService.getPost(pid);
		return new ResponseEntity<>(
				new SuccessResponse<>(HttpStatus.OK.value(), "게시글이 조회되었습니다.", post),
				HttpStatus.OK
		);
	}
	
	@PostMapping()
	public ResponseEntity<SuccessResponse<PostResponse>> createPost(@Valid @RequestBody PostRequest request) {		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		Object principal = authentication.getPrincipal();
		
		Long currentUid = null;
		if (principal instanceof PrincipalDetails) {
			currentUid = ((PrincipalDetails) principal).getUid();
		}
		
		PostResponse post = postService.createPost(request, currentUid);
		
		return new ResponseEntity<>(
				new SuccessResponse<>(HttpStatus.CREATED.value(), "게시글이 등록되었습니다.", post),
				HttpStatus.CREATED
		);
	}
	
	@PutMapping("/{pid}")
	public ResponseEntity<SuccessResponse<PostResponse>> updatePost(
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
		return new ResponseEntity<>(
				new SuccessResponse<>(HttpStatus.CREATED.value(), "게시글이 수정되었습니다.", post),
				HttpStatus.CREATED
		);
	}

	@DeleteMapping("/{pid}")
	public ResponseEntity<BaseResponse> deletePost(@PathVariable Long pid) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		Object principal = authentication.getPrincipal();
		
		Long currentUid = null;
		if (principal instanceof PrincipalDetails) {
			currentUid = ((PrincipalDetails) principal).getUid();
		}
		
		postService.deletePost(pid, currentUid);
		return new ResponseEntity<>(
				new BaseResponse(HttpStatus.NO_CONTENT.value(), "게시글이 삭제되었습니다."),
				HttpStatus.NO_CONTENT
		);
	}
}
