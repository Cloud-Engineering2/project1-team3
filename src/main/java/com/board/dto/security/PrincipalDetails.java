package com.board.dto.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.board.constant.UserRoleType;
import com.board.dto.UserDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 얘는 UserDto와 Security 객체 사이에 사용되기 때문에
 * UserDto가 될 수도 있어야 한다.
 * -> of & from, toDto 함수 필요
 */
@AllArgsConstructor
@Getter
@ToString
public class PrincipalDetails implements UserDetails {
	//필드 추가 (UserDto + Security 정보 포함)
    private Long uid;
    private String id;
    private String pw;
	private String username;
    private String uintro;
    private UserRoleType userRoleType;
    
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return pw;
	}
    
    public static PrincipalDetails of (Long uid, String id, String pw, String username, String uintro, UserRoleType userRoleType) {
    	return new PrincipalDetails (
	    			uid,
	    			id,
	    			pw,
	    			username,
	    			uintro,
					userRoleType
				);
    }
    
    public static PrincipalDetails from (UserDto userDto) {
    	return PrincipalDetails.of(
	    			userDto.getUid(),
	    			userDto.getId(),
	    			userDto.getPw(),
	    			userDto.getUsername(),
	    			userDto.getUintro(),
	    			userDto.getUserRoleType()
    			);
    }
    
    public UserDto toDto() {
    	return UserDto.of(uid, id, pw, username, uintro, userRoleType);
    }
	
    /*
     * getAuthorities 메서드에서는 사용자의 역할(Role) 또는 권한(Permission)을 반환
     * 사용자가 User인지 Admin인지를 반환한다.
     * */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		Collection<GrantedAuthority> collection = new ArrayList<GrantedAuthority>();
		collection.add(()-> userRoleType.getRoleType());
		
		return collection;
	}
	
}
