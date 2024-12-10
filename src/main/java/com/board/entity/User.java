package com.board.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.board.common.utils.UserRoleTypeAttributeConverter;
import com.board.constant.UserRoleType;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Entity
public class User extends AuditingFields {
	@Id
	@Column(length = 20, nullable=false)
    private String uid;
	
	@Column(length = 20, nullable=false)
    private String id;
	
	@Column(length = 20, nullable=false)
    private String pw;
	
	@Column(length = 20, nullable=false)
	private String username;
	
	@Column(length = 20)
	private String uintro;
    
	@Column(name = "role", columnDefinition = "VARCHAR(50)", nullable=false)
    @Convert(converter = UserRoleTypeAttributeConverter.class)
    private UserRoleType userRoleType;
	
    protected User() {}
    
	private User(String uid, String id, String username, String pw, String uintro, UserRoleType userRoleType) {
		this.uid = uid;
		this.id = id;
		this.username = username;
		this.pw = pw;
		this.uintro = uintro;
		this.userRoleType = userRoleType;
	}
	
}
