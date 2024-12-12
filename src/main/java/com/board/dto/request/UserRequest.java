package com.board.dto.request;

import com.board.constant.UserRoleType;
import com.board.dto.UserDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@AllArgsConstructor
@Getter
public class UserRequest {
	private String id;
    private String pw;
    private String username;
    private String uintro;
    private UserRoleType userRoleType;
    
    public static UserRequest of(String id, String pw, String username, String uintro) {
    	return new UserRequest(id, pw, username, uintro, null); 
    }
    
    public static UserRequest of(String id, String pw, String username, String uintro, UserRoleType userRoleType) {
    	return new UserRequest(id, pw, username, uintro, userRoleType); 
    }
    
    public UserDto toDto(UserRoleType userRole) {
    	return UserDto.of(id,
    					  pw,
    					  username, 
    					  uintro,  
    					  userRole);
    }
}
