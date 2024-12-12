package com.board.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.board.dto.security.PrincipalDetails;
import com.board.service.UserService;

@Configuration
public class SecurityConfig {
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    http
	        .csrf(csrf -> csrf.disable())
	        .authorizeHttpRequests(auth -> auth 
	        	// 로그인 여부와 상관없이 정적 파일에 접근 가능
	            .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
	                .permitAll()
	            .requestMatchers("/", "/loginPage", "/signUpPage")
	                .permitAll()
	            // 로그인과 로그아웃을 제외한 모든 요청에 대해 인증을 요구하는 설정
	            // 모든 Request에 대하여 로그인하지 않은 사용자는 접근할 수 없도록 설정
	            .anyRequest().authenticated()
	        )
	        .formLogin(formLogin -> formLogin
					.loginPage("/loginPage")   // 로그인 페이지 URL 설정
					.defaultSuccessUrl("/userInfoPage")	
					.failureUrl("/errorPage")
					.usernameParameter("id")			// 아이디 파라미터명 설정
					.passwordParameter("pw")			// 패스워드 파라미터명 설정
					.loginProcessingUrl("/login")
	        )
	        .logout(logout -> logout.logoutSuccessUrl("/loginPage"));

	    return http.build();
	}
	
	// 인증된 데이터를 가져오는 로직 (id, password 비교)
	@Bean
	public UserDetailsService userDetailsService(UserService userService) {
		return id -> userService.selectUserById(id)
										.map(PrincipalDetails::from)
										.orElseThrow();
	}
	
    // 현재 DB에는 암호화 X -> 이를 그대로 사용하기 위한 Encoder 설정
	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}

}