package com.board.entity;

import com.board.common.utils.UserRoleTypeAttributeConverter;
import com.board.constant.UserRoleType;
import com.board.dto.UserDto;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
@Entity
public class User extends AuditingFields {
	@Id
	@Column(length = 20, nullable=false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uid;
	
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
    
	private User(Long uid, String id, String pw, String username, String uintro, UserRoleType userRoleType) {
		this.uid = uid;
		this.id = id;
		this.pw = pw;
		this.username = username;
		this.uintro = uintro;
		this.userRoleType = userRoleType;
	}
	
	public static User of(Long uid, String id, String pw, String username, String uintro, UserRoleType userRoleType) {
		return new User(uid, id, pw, username, uintro, userRoleType);
	}
	
    public UserDto toDto(UserRoleType userRole) {
    	return UserDto.of(uid, 
    					  id,
    					  pw,
    					  username, 
    					  uintro,  
    					  userRole);
    }
	
}
