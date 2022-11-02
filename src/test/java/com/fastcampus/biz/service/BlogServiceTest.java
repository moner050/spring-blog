package com.fastcampus.biz.service;

import javax.transaction.Transactional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fastcampus.biz.domain.Blog;
import com.fastcampus.biz.domain.BlogUser;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class BlogServiceTest {

	@Autowired
	private BlogService blogService;
	
	@Autowired
	private BlogUserService blogUserService;
	
	@Test
	@Transactional
	@DisplayName("블로그생성 테스트")
	void 블로그생성_테스트() {
		// given
		BlogUser user = new BlogUser();
		Blog blog = new Blog();
		Blog myBlog = new Blog();
		
		// when
		user = blogUserService.getUser("test");
		
		blog = Blog.builder()
			.blogId(user.getUserId())
			.title("테스트 블로그")
			.build();
		
		blogService.insertBlog(blog);
		
		myBlog = blogService.getBlog(user.getUserId());
		
		// then
		assertThat(myBlog.getBlogId()).isEqualTo(user.getUserId());
		assertThat(myBlog.getTitle()).isEqualTo("테스트 블로그");
		assertThat(myBlog.getStatus()).isEqualTo("운영");
		assertThat(myBlog.getTag()).isEqualTo("No Tag");
	}
	
	@Test
	@DisplayName("유저조회 테스트")
	void 유저조회_테스트() {
		// given
		BlogUser user = new BlogUser();
		
		// when
		user = blogUserService.getUser("test");
		
		// then
		assertThat(user.getName()).isEqualTo("이민형");

	}
}
