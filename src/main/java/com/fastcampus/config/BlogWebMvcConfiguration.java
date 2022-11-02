package com.fastcampus.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class BlogWebMvcConfiguration {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
		// 인증없이 접근을 허용할 목록
		.authorizeHttpRequests().antMatchers("/", "/auth/**", "/blog/?", "/search", "/js/**", "/images/**", "/webjars/**", "/reset").permitAll()
		// 위의 경로 이외에는 모두 로그인 인증을 거치도록 설정
		.anyRequest().authenticated()
		.and()
		// CSRF 토큰을 전달하지 않도록 설정
		.csrf().disable()
		// 사용자가 만든 로그인 화면 띄우기
		.formLogin().loginPage("/auth/login")
		.and()
		// 사용자가 만든 로그아웃 페이지 및 로그아웃 성공 페이지 설정
		.logout().logoutUrl("/auth/logout").logoutSuccessUrl("/");
		
		return http.build();
	}
}
