package com.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.board.constant.UserRoleType;
import com.board.dto.request.UserRequest;
import com.board.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class UserController {
	
	private final UserService userService;
	
    @GetMapping("/")
    public String mainPage() {
		return "login";
    } 
	
	@GetMapping("/signUpPage")
	public String signUpPage() {
        return "signUp";
	} 

	@PostMapping("/signUp")
	public String signUp (@ModelAttribute UserRequest user) {
		
		userService.insertUser(user.toDto(UserRoleType.USER));
        return "login";
	} 
	
    @GetMapping("/loginPage")
	public String loginPage () {
        return "login";
	}
	
	@PostMapping("/login")
	public void login (@ModelAttribute UserRequest user) {
    	//정보 확인 후 posts 페이지로 보내기
	}
	
	@GetMapping("/logout")
	public String logout () {
    	//로그 아웃 후 로그인 페이지로 보내기
        return "login";
	} 
	
	@GetMapping("/userInfoPage")
	public String userInfoPage () {
    	//로그 아웃 후 로그인 페이지로 보내기
        return "/userInfo";
	} 
	
	@GetMapping("/errorPage")
	public String errorPage () {
    	//로그 아웃 후 로그인 페이지로 보내기
        return "errorPage";
	} 
}
