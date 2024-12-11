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
}
