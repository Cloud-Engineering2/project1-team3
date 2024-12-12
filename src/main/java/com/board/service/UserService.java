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
		Optional<UserDto> dto = selectUserById(userDto.getId());
		// 있는지 여부 확인하기
		if(!dto.isPresent()) {
			
			System.out.println(userDto);
			
			User user = userDto.toEntity();
			
			System.out.println(user);
			
			userRepository.save(user);
		}
	}
	
	public UserDto selectUserByUid (Long uid) {
	    // Optional의 값이 있을 때만 변환하여 반환
	    return userRepository.findById(uid)
	            .map(UserDto::from)  // User가 있을 경우 UserDto로 변환
	            .orElse(null);        // User가 없을 경우 null 반환
	}
	
	public Optional<UserDto> selectUserById(String id) {
	    return userRepository.findById(id)
	            .map(UserDto::from);  // User가 있을 경우 UserDto로 변환
	}
	
	public void updateUser (UserDto userDto) {
		User user = userDto.toEntity();
		userRepository.save(user);
	}
	
}
