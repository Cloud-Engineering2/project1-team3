package com.board.dto;

import java.time.LocalDateTime;

import com.board.constant.UserRoleType;
import com.board.entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@ToString
@Getter
@Setter
public class UserDto {
    private Long uid;
    private String id;
    private String pw;
	private String username;
    private String uintro;
    private UserRoleType userRoleType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    
    public static UserDto of(String id, String pw, String username, String uintro, UserRoleType userRoleType) {
    	return UserDto.of(null, id, pw, username, uintro, userRoleType, null, null);
	}
    
    public static UserDto of(Long uid, String id, String pw, String username, String uintro, UserRoleType userRoleType) {
    	return UserDto.of(uid, id, pw, username, uintro, userRoleType, null, null);
	}
    
    public static UserDto of(Long uid, String id, String pw, String username, String uintro, UserRoleType userRoleType,
			LocalDateTime createdAt, LocalDateTime updatedAt) {
    	return new UserDto(uid, id, pw, username, uintro, userRoleType, createdAt, updatedAt);
    }
    
    public static UserDto from(User user) {
    	return new UserDto(
	    					user.getUid(),
	    					user.getId(),
	    					user.getPw(),
	    					user.getUsername(),
	    					user.getUintro(),
	    					user.getUserRoleType(),
	    					user.getCreatedAt(),
	    					user.getUpdatedAt()
		);
    }
    
    public User toEntity() {
    	return User.of(
						uid,
						id,
						pw,
						username, 
						uintro, 
						userRoleType
		);
    }

}
