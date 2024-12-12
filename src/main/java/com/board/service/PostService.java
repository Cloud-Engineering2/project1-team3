package com.board.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.board.constant.Category;
import com.board.dto.request.PostRequest;
import com.board.dto.response.PostResponse;
import com.board.dto.response.PostsResponse;
import com.board.entity.Post;
import com.board.entity.User;
import com.board.exception.PostNotFoundException;
import com.board.exception.UnauthorizeException;
import com.board.repository.PostRepository;
import com.board.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {
	private final PostRepository postRepository;
	private final UserRepository userRepository;
	
	@Transactional(readOnly = true)
	public List<PostsResponse> getPosts() {
		List<Post> posts = postRepository.findAll();
		return posts.stream()
				.map(PostsResponse::convertToDto)
				.toList();
	}

	@Transactional
	public PostResponse createPost(PostRequest request, Long uid) {
		User user = userRepository.findById(uid).get();
		
		// 요약은 임시로 content 내용을 그대로 사용
		Post post = Post.of(request.getTitle(), request.getContent(), request.getContent(), Category.valueOf(request.getCategory()), user);
		postRepository.save(post);
		return PostResponse.convertToDto(post);
	}

	public PostResponse updatePost(Long pid, PostRequest request, Long uid) {
		User user = userRepository.findById(uid).get();
		Post post = postRepository.findById(pid).orElseThrow(() -> new PostNotFoundException(pid));
		
		if (!user.getUid().equals(post.getUser().getUid())) {
			throw new UnauthorizeException("Post에 대한 권한이 없습니다. Post ID - " + pid);
		}
		
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
	public void deletePost(Long pid, Long uid) {
		User user = userRepository.findById(uid).get();
		Post post = postRepository.findById(pid).orElseThrow(() -> new PostNotFoundException(pid));
		
		if (!user.getUid().equals(post.getUser().getUid())) {
			throw new UnauthorizeException("Post에 대한 권한이 없습니다. Post ID - " + pid);
		}
		
		postRepository.delete(post);
	}

	@Transactional(readOnly = true)
	public PostResponse getPost(Long pid) {
		Post post = postRepository.findById(pid).orElseThrow(() -> new PostNotFoundException(pid));
		return PostResponse.convertToDto(post);
	}

	@Transactional(readOnly = true)
	public List<PostsResponse> getPostsByCategory(Category category) {
		List<Post> posts = postRepository.findByCategory(category);
		return posts.stream()
				.map(PostsResponse::convertToDto)
				.toList();
	}
}
