package com.board.entity;

import java.util.LinkedHashSet;
import java.util.Set;

import com.board.constant.Category;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
@Entity
public class Post extends AuditingFields {
	@Id
	@Column(name = "pid")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String title;
	
	private String content;
	
	private String summary;
	
	@Enumerated(EnumType.STRING)
	private Category category;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "uid")
	private User user;

    @ToString.Exclude
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private final Set<Comment> postComments = new LinkedHashSet<>();
    
	protected Post() {}
	
    private Post(String title, String content, String summary, Category category, User user) {
    	this.title = title;
    	this.content = content;
    	this.summary = summary;
    	this.category = category;
    	this.user = user;
    }
    
    public static Post of(String title, String content, String summary, Category category, User user) {
    	return new Post(title, content, summary, category, user);
    }
    
    public void updatePost(String title, String content, String summary, Category category) {
    	this.title = title;
    	this.content = content;
    	this.summary = summary;
    	this.category = category;
    }
}
