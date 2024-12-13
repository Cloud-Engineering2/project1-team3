package com.board.service;

import com.board.dto.CommentDto;
import com.board.dto.CommentDto.CommentRequest;
import com.board.dto.CommentDto.CommentResponse;
import com.board.entity.Comment;
import com.board.entity.Post;
import com.board.entity.User;
import com.board.exception.PostNotFoundException;
import com.board.repository.CommentRepository;
import com.board.repository.PostRepository;
import com.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    // 댓글 작성
//    public Comment registerComment(CommentDto.CommentRequest content, Long userId, Long postId) {
//    	
//    	// 작성자 조회
//        User user = userRepository.getReferenceById(userId);
//        // 게시글 조회
//        Post post = postRepository.getReferenceById(postId);
//        // 댓글 생성
//        Comment comment = Comment.of(content.getContent(), post, user);
//        // 댓글 저장
//        return commentRepository.save(comment);
//    }

    @Transactional
	public CommentResponse registerComment(CommentRequest request, Long pid, Long uid) {
		Post post = postRepository.findById(pid).orElseThrow(() -> new PostNotFoundException(pid));
		User user = userRepository.findById(uid).get();
		
		Comment comment = Comment.of(request.getContent(), post, user);
		commentRepository.save(comment);
		return CommentResponse.from(comment);
	}

    // 댓글 삭제
    @Transactional
    public void deleteComment(Long commentId, Long userId) {
    	
    	// 댓글 조회
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        // 작성자 확인
        if (!comment.getUser().getUid().equals(userId)) {
            throw new RuntimeException("You do not have permission to delete this comment.");
        }

        // 댓글 삭제
        commentRepository.delete(comment);
    }

    // 댓글 수정
    @Transactional
    public CommentResponse updateComment(Long commentId, Long userId, CommentRequest newContent) {
    	
    	// 댓글 조회
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        // 작성자 확인
        if (!comment.getUser().getUid().equals(userId)) {
            throw new RuntimeException("You do not have permission to update this comment.");
        }

        // 댓글 내용 수정
        comment.setContent(newContent.getContent());
        return CommentResponse.from(comment);
        // 수정 시간은 AuditingFields에서 자동으로 업데이트됨
    }


}
