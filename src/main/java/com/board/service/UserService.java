package com.board.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.board.dto.UserDto;
import com.board.entity.User;
import com.board.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
	
	private final UserRepository userRepository;

	public void insertUser (UserDto userDto) {
//		User user = userDto.toEntity();
//		userRepository.save(user);
	}
	
	
	public Optional<UserDto> selectUser (String uid) {
//		return userRepository.findById(uid)
//								.map(UserDto::from);
		return null;
	}
}
