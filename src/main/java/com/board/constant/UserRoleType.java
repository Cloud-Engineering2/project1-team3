package com.board.constant;

import java.util.Arrays;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public enum UserRoleType {
	USER("USER"),
	ADMIN("ADMIN");
	
	private String roleType;
	
	UserRoleType(String roleType) {
		this.roleType = roleType;
	}
	
	public static UserRoleType getInstance(String roleType) {
		return Arrays.stream(UserRoleType.values())
					.filter(role -> role.getRoleType().equals(roleType))
					.findFirst()
					.orElseThrow();
	}
}
