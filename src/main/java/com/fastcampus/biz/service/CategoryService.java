package com.fastcampus.biz.service;

import java.util.ArrayList;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fastcampus.biz.domain.Category;
import com.fastcampus.biz.persistence.CategoryRepository;

@Service
@Transactional
public class CategoryService {

	private CategoryRepository categoryRepository;

	@Autowired
	public CategoryService(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}
	
	// 카테고리 검색 (블로그 아이디)
	public Category searchCategoryBlogId(Long id) {
		Optional<Category> category = categoryRepository.findByBlogBlogId(id);
		
		return category.orElseGet(() -> new Category());
	}
	
	// 카테고리 검색 (블로그 아이디, 카테고리 아이디)
	public Category searchCategoryBlogIdAndCategoryId(Long blogId, Long categoryId) {
		Optional<Category> category = categoryRepository.findByBlogBlogIdAndCategoryId(blogId, categoryId);
		
		return category.orElseGet(() -> new Category());
	}
	
	// 카테고리 검색 (카테고리명)
	public Category getCategoryCategoryName(String name){
		Optional<Category> category = categoryRepository.findByCategoryName(name);
		
		return category.orElseGet(() -> new Category());
	}
	
	// 카테고리 검색 (블로그아이디, 카테고리명)
	public Category getCategoryBlogIdAndCategoryName(Long id, String name){
		Optional<Category> category = categoryRepository.findByBlogBlogIdAndCategoryName(id, name);
		
		return category.orElseGet(() -> new Category());
	}
	
	// 카테고리 목록 검색 (블로그아이디)
	public ArrayList<Category> getCategoryListBlogId(Long id){
		return categoryRepository.findAllByBlogBlogId(id);
	}
	
	
	// 카테고리 목록 검색 (블로그아이디)
	public Page<Category> getCategoryListBlogId(Pageable pageable, Long id){
		return categoryRepository.findAllByBlogBlogId(pageable, id);
	}
	
	// 카테고리 저장
	public void insertCategory(Category category) {
		categoryRepository.save(category);
	}
	
	// 카테고리 업데이트
	public void updateCategory(Category category, Long id) {
		Optional<Category> myCategory = categoryRepository.findById(id);
		if(myCategory.isPresent()) categoryRepository.save(myCategory.get());
	}
	
	// 카테고리 삭제
	public void deleteCategory(Category category) {
		categoryRepository.delete(category);
	}
	
}
