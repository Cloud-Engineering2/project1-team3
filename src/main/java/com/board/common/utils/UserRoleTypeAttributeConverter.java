package com.board.common.utils;

import com.board.constant.UserRoleType;

import jakarta.persistence.AttributeConverter;

public class UserRoleTypeAttributeConverter implements AttributeConverter<UserRoleType, String>{
	
	@Override
	public String convertToDatabaseColumn(UserRoleType attribute) {
		return attribute.getRoleType();
	}

	@Override
	public UserRoleType convertToEntityAttribute(String dbData) {
		return UserRoleType.getInstance(dbData);
	}
}
