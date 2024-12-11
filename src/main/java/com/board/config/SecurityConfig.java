/*
 * package com.board.config;
 * 
 * 
 * import static org.springframework.security.config.Customizer.withDefaults;
 * 
 * import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
 * import org.springframework.context.annotation.Bean; import
 * org.springframework.context.annotation.Configuration; import
 * org.springframework.security.config.annotation.web.builders.HttpSecurity;
 * import org.springframework.security.web.SecurityFilterChain;
 * 
 * @Configuration public class SecurityConfig {
 * 
 * @Bean public SecurityFilterChain securityFilterChain(HttpSecurity http)
 * throws Exception { http .csrf(csrf -> csrf.disable())
 * .authorizeHttpRequests(auth -> auth
 * .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
 * .permitAll() .requestMatchers("/", "/posts", "/sign-up") .permitAll()
 * .anyRequest().authenticated() ) .formLogin(formLogin -> formLogin
 * .loginPage("/login") // 로그인 페이지 URL 설정 .permitAll() // 모든 사용자가 접근 가능하도록 허용 )
 * .logout(logout -> logout.logoutSuccessUrl("/"));
 * 
 * return http.build(); }
 * 
 * }
 */