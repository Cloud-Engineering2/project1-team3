package com.board.config;

import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.board.dto.security.PrincipalDetails;

@EnableJpaAuditing
@Configuration
public class JpaConfig {
	@Bean
	AuditorAware<String> auditorAware() {
		
		return ()->Optional.ofNullable(SecurityContextHolder.getContext())
							.map(SecurityContext::getAuthentication)
							.filter(Authentication::isAuthenticated)
							.map(Authentication::getPrincipal) // 현재 로그인한 사람들의 정보
							.map(principal -> {
								if(principal instanceof PrincipalDetails) {
									return ((PrincipalDetails)principal).getUid().toString();
								} else if (principal instanceof String) {
									return (String) principal;
								} else {
                                    return null; 
								}
							});
	}
}

/*
 * 1. Spring Security에서는 사용자의 인증 정보를 SecurityContextHolder에 저장
 * 2. SecurityContext에서 인증 객체(Authentication)를 가져온다. Authentication은 현재 인증된 사용자의 정보를 포함
 * 3. 인증된 사용자인지 확인
 * 4. 인증 객체에서 Principal 정보를 획득
 * 5. Principal이 PrincipalDetails 객체인 경우, 사용자 ID(getUid())를 반환
 * */