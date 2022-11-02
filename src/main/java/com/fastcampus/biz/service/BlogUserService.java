package com.fastcampus.biz.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fastcampus.biz.domain.BlogUser;
import com.fastcampus.biz.persistence.BlogUserRepository;

@Service
@Transactional
public class BlogUserService {

	private BlogUserRepository blogUserRepository;
	
	@Autowired
	public BlogUserService(BlogUserRepository blogUserRepository) {
		this.blogUserRepository = blogUserRepository;
	}
	
	// 유저 정보 조회
	public BlogUser getUser(String username) {
		Optional<BlogUser> findUser = blogUserRepository.findByUsername(username);
		
		// 만약 유저 정보가 있다면 유저정보를 리턴.
		return findUser.orElseGet(() -> new BlogUser());
	}
}
