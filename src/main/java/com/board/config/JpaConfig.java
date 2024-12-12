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

		// 로그인한 사람들이 생성 및 변경 할 수 있도록 한다.
		// SecurityContextHolder에 SecuritySession이 저장 된다.
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
