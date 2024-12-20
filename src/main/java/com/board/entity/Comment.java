package com.board.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
@Entity
public class Comment extends AuditingFields{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 200)
    private String content;
    
    @ManyToOne(optional = false) 
    @JoinColumn(name = "pid", referencedColumnName = "pid")
    private Post post;

    @ManyToOne(optional = false)
    @JoinColumn(name = "uid", referencedColumnName = "uid")
    private User user;

	protected Comment() {}
	
	private Comment(String content, Post post, User user) {
		this.content = content;
		this.post = post;
		this.user = user;
	}
    
	public static Comment of(String content, Post post, User user) {
		return new Comment(content, post, user);
	}

	public void setContent(String newContent) {
        if (newContent == null || newContent.trim().isEmpty()) {
            throw new IllegalArgumentException("Comment content cannot be null or empty");
        }
        this.content = newContent;
    }
}
