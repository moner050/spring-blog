package com.fastcampus.biz.persistence;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fastcampus.biz.domain.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

	Optional<Category> findByBlogBlogId(Long id);
	
	Optional<Category> findByCategoryName(String name);
	
	Optional<Category> findByBlogBlogIdAndCategoryName(Long id, String name);
	
	Optional<Category> findByBlogBlogIdAndCategoryId(Long blogId, Long categoryId);

	ArrayList<Category> findAllByBlogBlogId(Long id);
	
	Page<Category> findAllByBlogBlogId(Pageable pageable, Long id);
	
}
