package com.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.board.dto.UserDto;
import com.board.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class UserController {
	
	private final UserService userService;
	
	@GetMapping("/")
	public String mainPage() {
		return "userInfo";
	} 
	
	@GetMapping("/sign-up")
	public String signupPage() {
		return "sign-up Page";
	} 

	@PostMapping("/sign-up")
	public String signup( UserDto userRequest ) {
		return "sign-up";
	} 
	
	@GetMapping("/login")
	public String loginPage () {
		return "login Page";
	} 
	
	@PostMapping("/login")
	public String login () {
		return "login";
	} 
	
	@GetMapping("/logout")
	public String logout () {
		return "logout";
	} 
}
