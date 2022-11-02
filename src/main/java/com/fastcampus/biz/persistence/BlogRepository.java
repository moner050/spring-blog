package com.fastcampus.biz.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fastcampus.biz.domain.Blog;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {

	// 블로그 제목으로 검색하기 (대소문자 무시)
	Page<Blog> findAllByTitleContainingIgnoreCase(Pageable pageable, String title);
	
	// 블로그 태그로 검색하기 (대소문자 무시)
	Page<Blog> findAllByTagContainingIgnoreCase(Pageable pageable, String tag);
}
