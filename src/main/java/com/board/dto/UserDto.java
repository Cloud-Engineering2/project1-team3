package com.board.dto;

import java.time.LocalDateTime;

import com.board.constant.UserRoleType;

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

}
