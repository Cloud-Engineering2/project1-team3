package com.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	@Autowired
	private AzureSummarization azureSummarization;
	
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
		
		String content = request.getContent();
		String summary = determineSummary(content);
		
		Post post = Post.of(request.getTitle(), request.getContent(), summary, Category.valueOf(request.getCategory()), user);
		postRepository.save(post);
		return PostResponse.convertToDto(post);
	}

	@Transactional
	public PostResponse updatePost(Long pid, PostRequest request, Long uid) {
		User user = userRepository.findById(uid).get();
		Post post = postRepository.findById(pid).orElseThrow(() -> new PostNotFoundException(pid));
		
		if (!user.getUid().equals(post.getUser().getUid())) {
			throw new UnauthorizeException("Post에 대한 권한이 없습니다. Post ID - " + pid);
		}

		String content = request.getContent();
		String summary;

	    if (post.getContent().equals(content)) {
	        summary = post.getSummary(); // 게시글 내용 미변경 시 기존 요약 사용
	    } else {
	        summary = determineSummary(content);
	    }
	    
		post.updatePost(
			request.getTitle(),
			content,
			summary,
			Category.valueOf(request.getCategory())
		);
		
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
	
	private String determineSummary(String content) {
	    if (content.length() <= 250) {
	        return content;
	    } else {
	        return azureSummarization.summarizeContent(content);
	    }
	}
}
