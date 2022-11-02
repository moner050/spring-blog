package com.fastcampus.biz.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fastcampus.biz.domain.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

	Page<Post> findAllByBlogBlogId(Long id, Pageable pageable);
	
	Page<Post> findAllByBlogBlogIdAndCategoryCategoryId(Pageable pageable, Long blogId, Long categoryId);
}
