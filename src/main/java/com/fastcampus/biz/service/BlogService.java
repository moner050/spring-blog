package com.fastcampus.biz.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fastcampus.biz.domain.Blog;
import com.fastcampus.biz.persistence.BlogRepository;

@Service
@Transactional
public class BlogService {

	private BlogRepository blogRepository;

	@Autowired
	public BlogService(BlogRepository blogRepository) {
		this.blogRepository = blogRepository;
	}

	// 블로그 등록
	public void insertBlog(Blog blog) {
		blogRepository.save(blog);
	}
	
	// 블로그 수정
	public void updateBlog(Blog blog) {
		blogRepository.save(blog);
	}
	
	// 블로그 목록
	public Page<Blog> getBlogList(Pageable pageable){
		return blogRepository.findAll(pageable);
	}
	
	// 블로그 상세보기
	public Blog getBlog(Long id) {
		Optional<Blog> findBlog = blogRepository.findById(id);
		
		// 만약 블로그가 있다면 블로그 정보를 리턴
		return findBlog.orElseGet(() -> new Blog());
	}
	
	// 블로그 제목으로 검색하기
	public Page<Blog> getBlogByTitle(Pageable pageable, String title){
		return blogRepository.findAllByTitleContainingIgnoreCase(pageable, title);
	}
	
	// 블로그 태그로 검색하기
	public Page<Blog> getBlogByTag(Pageable pageable, String tag){
		return blogRepository.findAllByTagContainingIgnoreCase(pageable, tag);
	}
	
	// 블로그 삭제하기
	public void deleteBlog(Blog blog) {
		blogRepository.delete(blog);
	}
}
