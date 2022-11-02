package com.fastcampus.security.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fastcampus.biz.domain.BlogUser;
import com.fastcampus.biz.persistence.BlogUserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private BlogUserRepository blogUserRepository;
	
	@Autowired
	public UserDetailsServiceImpl(BlogUserRepository blogUserRepository) {
		this.blogUserRepository = blogUserRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		BlogUser blogUser = blogUserRepository.findByUsername(username).get();
		return new UserDetailsImpl(blogUser);
	}

}
