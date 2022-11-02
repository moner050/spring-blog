package com.fastcampus;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.fastcampus.biz.domain.BlogUser;
import com.fastcampus.biz.persistence.BlogUserRepository;

@SpringBootApplication
public class FinalToyProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinalToyProjectApplication.class, args);
	}

	// 시작시 ADMIN 권한과 USER 권한이 있는 유저 INSERT
	@Bean
	public CommandLineRunner initData(BlogUserRepository blogUserRepository) {
		return (arg) -> {
			BlogUser adminUser = BlogUser.builder()
					.userId(1L)
					.username("test")
					.password("test")
					.name("이민형")
					.role("ADMIN")
					.build();
			
			BlogUser user = BlogUser.builder()
					.userId(2L)
					.username("aaa")
					.password("aaa")
					.name("방문자")
					.role("USER")
					.build();
					
			blogUserRepository.save(user);
			blogUserRepository.save(adminUser);
		};
	}
}
