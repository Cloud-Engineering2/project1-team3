package com.board.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.board.constant.Category;
import com.board.dto.request.PostRequest;
import com.board.dto.response.PostResponse;
import com.board.entity.Post;
import com.board.repository.PostRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {
	private final PostRepository postRepository;
	
	@Transactional(readOnly = true)
	public List<PostResponse> getPosts() {
		List<Post> posts = postRepository.findAll();
		return posts.stream()
				.map(PostResponse::convertToDto)
				.toList();
	}

	@Transactional
	public PostResponse createPost(PostRequest request) {
		// 요약은 임시로 content 내용을 그대로 사용
		Post post = Post.of(request.getTitle(), request.getContent(), request.getContent(), Category.valueOf(request.getCategory()), null);
		postRepository.save(post);
		return PostResponse.convertToDto(post);
	}

	public PostResponse updatePost(Long pid, PostRequest request) {
		Post post = postRepository.findById(pid).orElseThrow(() -> new PostNotFoundException(pid));
		
		// 요약 API 사용 횟수 감소 목적
		// content의 변경 사항이 없으면 기존의 요약을 사용
		if (post.getContent().equals(request.getContent())) {
			post.updatePost(
					request.getTitle(),
					request.getContent(),
					post.getSummary(),
					Category.valueOf(request.getCategory())
			);
		} else {
			post.updatePost(
					request.getTitle(),
					request.getContent(),
					request.getContent(),	// 임시로 content 내용 그대로 사용
					Category.valueOf(request.getCategory())
			);
		}
		
		postRepository.save(post);
		
		return PostResponse.convertToDto(post);
	}

	@Transactional
	public void deletePost(Long pid) {
		Post post = postRepository.findById(pid).orElseThrow(() -> new PostNotFoundException(pid));
		
		postRepository.delete(post);
	}
}
