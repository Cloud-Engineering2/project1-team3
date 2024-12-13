package com.board.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.board.common.SuccessResponse;
import com.board.common.base.BaseResponse;
import com.board.dto.CommentDto;
import com.board.dto.CommentDto.CommentRequest;
import com.board.dto.CommentDto.CommentResponse;
import com.board.dto.security.PrincipalDetails;
import com.board.entity.Comment;
import com.board.service.CommentService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/posts/{pid}/comments")
public class CommentController {

    private final CommentService commentService;

    // 댓글 작성
//    @PostMapping
//
//    public ResponseEntity<CommentDto.CommentResponse> registerComment(
//            @RequestParam Long postId,
//            @RequestParam Long userId,
//            @RequestBody CommentDto.CommentRequest content) {
//        Comment svaedComment = commentService.registerComment(postId, userId, content);
//        return ResponseEntity.ok(CommentDto.CommentResponse.from(svaedComment));
//    }
    
    @PostMapping
    public ResponseEntity<SuccessResponse<CommentResponse>> registerComment(
    		@PathVariable Long pid,
            @RequestBody CommentDto.CommentRequest content
    ) {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		Object principal = authentication.getPrincipal();
		
		Long currentUid = null;
		if (principal instanceof PrincipalDetails) {
			currentUid = ((PrincipalDetails) principal).getUid();
		}
		
        CommentResponse comment = commentService.registerComment(content, pid, currentUid);
        return new ResponseEntity<>(
				new SuccessResponse<>(HttpStatus.CREATED.value(), "댓글이 등록되었습니다.", comment),
				HttpStatus.CREATED
		);
    }

    // 댓글 삭제
    @DeleteMapping("/{commentId}")
    public ResponseEntity<BaseResponse> deleteComment(@PathVariable Long commentId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		Object principal = authentication.getPrincipal();
		
		Long currentUid = null;
		if (principal instanceof PrincipalDetails) {
			currentUid = ((PrincipalDetails) principal).getUid();
		}
		
        commentService.deleteComment(commentId, currentUid);
        return new ResponseEntity<>(
				new BaseResponse(HttpStatus.NO_CONTENT.value(), "댓글이 삭제되었습니다."),
				HttpStatus.NO_CONTENT
		);
    }

    // 댓글 수정
    @PutMapping("/{commentId}")
    public ResponseEntity<SuccessResponse<CommentResponse>> updateComment(
            @PathVariable Long commentId,
            @RequestBody CommentRequest newContent) {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		Object principal = authentication.getPrincipal();
		
		Long currentUid = null;
		if (principal instanceof PrincipalDetails) {
			currentUid = ((PrincipalDetails) principal).getUid();
		}
		
        CommentResponse updatedComment = commentService.updateComment(commentId, currentUid, newContent);
        return new ResponseEntity<>(
				new SuccessResponse<>(HttpStatus.CREATED.value(), "댓글이 수정되었습니다.", updatedComment),
				HttpStatus.CREATED
		);
    }
}
