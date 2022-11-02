package com.fastcampus.web.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fastcampus.biz.domain.Blog;
import com.fastcampus.biz.domain.Category;
import com.fastcampus.biz.domain.DisplayType;
import com.fastcampus.biz.dto.CategoryDto;
import com.fastcampus.biz.service.BlogService;
import com.fastcampus.biz.service.CategoryService;
import com.fastcampus.security.jpa.UserDetailsImpl;

@Controller
public class CategoryController {

	private BlogService blogService;
	private CategoryService categoryService;

	@Autowired
	public CategoryController(BlogService blogService, CategoryService categoryService) {
		this.blogService = blogService;
		this.categoryService = categoryService;
	}

	// 카테고리 관리 페이지 가기
	@GetMapping("/blog/setting/category")
	public String getCategoryList(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, Model model,
			@PageableDefault(size = 10, sort = "categoryId", direction = Direction.DESC) Pageable pageable) {
		Optional<Blog> blog = Optional.ofNullable(blogService.getBlog(userDetailsImpl.getUser().getUserId()));
		
		if(blog.isPresent()) {
			Optional<Page<Category>> category = Optional.ofNullable(categoryService.getCategoryListBlogId(pageable, blog.get().getBlogId()));
			
			if(category.isPresent()) {
				model.addAttribute("blog", blog.get());
				model.addAttribute("categoryList", category.get());
				return "category/getCategoryList";
			}
		}
		return "welcome";
	}
	
	// 카테고리 수정 페이지 가기
	@GetMapping("/blog/setting/category/update/{id}")
	public String getUpdateCategory(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl, Model model,
			@PageableDefault(size = 10, sort = "categoryId", direction = Direction.DESC) Pageable pageable) {
		Optional<Blog> blog = Optional.ofNullable(blogService.getBlog(userDetailsImpl.getUser().getUserId()));
		
		if(blog.isPresent()) {
			Optional<Page<Category>> category = Optional.ofNullable(categoryService.getCategoryListBlogId(pageable, blog.get().getBlogId()));
			
			if(category.isPresent()) {
				model.addAttribute("blog", blog.get());
				model.addAttribute("category", categoryService.searchCategoryBlogIdAndCategoryId(blog.get().getBlogId(), id));
				model.addAttribute("categoryList", category.get());
				return "category/getCategoryList";
			}
		}
		return "welcome";
	}
	
	// 카테고리 수정하기
	@PostMapping("/blog/setting/category/update")
	@ResponseBody
	public String updateCategory(@RequestBody CategoryDto categoryDto,@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, Model model,
			@PageableDefault(size = 10, sort = "categoryId", direction = Direction.DESC) Pageable pageable) {
		Optional<Blog> blog = Optional.ofNullable(blogService.getBlog(userDetailsImpl.getUser().getUserId()));
		
		DisplayType displayType = null;
		
		if(blog.isPresent()) {
			
			if(categoryDto.getDisplayType().equals(DisplayType.ALL.getType())) {
				displayType = DisplayType.ALL;
			}
			else {
				displayType = DisplayType.TITLE;
			}

			Category myCategory = categoryService.searchCategoryBlogIdAndCategoryId(blog.get().getBlogId(), categoryDto.getCategoryId());
			
			myCategory = Category.builder()
					.categoryId(categoryDto.getCategoryId())
					.categoryName(categoryDto.getCategoryName())
					.displayType(displayType)
					.description(categoryDto.getDescription())
					.build();
			
			myCategory.setBlog(blog.get());
			
			Optional<Page<Category>> category = Optional.ofNullable(categoryService.getCategoryListBlogId(pageable, blog.get().getBlogId()));
			
			if(category.isPresent()) {
				categoryService.insertCategory(myCategory);
				return "카테고리 수정이 완료되었습니다.";
			}
		}
		return "카테고리 수정에 실패했습니다.";
	}
	
	
	// 카테고리 추가하기
	@PostMapping("/blog/setting/category/insert")
	@ResponseBody
	public String insertCategory(@RequestBody CategoryDto categoryDto,@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, Model model,
			@PageableDefault(size = 10, sort = "categoryId", direction = Direction.DESC) Pageable pageable) {
		Optional<Blog> blog = Optional.ofNullable(blogService.getBlog(userDetailsImpl.getUser().getUserId()));
		
		DisplayType displayType = null;
		
		if(blog.isPresent()) {
			
			Optional<Category> check = Optional.ofNullable(categoryService.getCategoryBlogIdAndCategoryName(blog.get().getBlogId(), categoryDto.getCategoryName()));
			
			// 중복되는 카테고리 명이 있으면
			if(check.get().getCategoryId() != null) {
				return "이미 존재하는 카테고리입니다.";
			}
			
			if(categoryDto.getDisplayType().equals(DisplayType.ALL.getType())) {
				displayType = DisplayType.ALL;
			}
			else {
				displayType = DisplayType.TITLE;
			}

			Category myCategory = Category.builder()
					.categoryName(categoryDto.getCategoryName())
					.displayType(displayType)
					.description(categoryDto.getDescription())
					.build();
			
			myCategory.setBlog(blog.get());
			
			Optional<Page<Category>> category = Optional.ofNullable(categoryService.getCategoryListBlogId(pageable, blog.get().getBlogId()));
			
			if(category.isPresent()) {
				// 카테고리 추가
				categoryService.insertCategory(myCategory);
				return "카테고리 추가가 완료되었습니다.";
			}
		}
		return "카테고리 추가에 실패했습니다.";
	}
	
	// 카테고리 삭제하기
	@DeleteMapping("/blog/setting/category/delete/{id}")
	@ResponseBody
	public String deleteCategory(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
		Optional<Blog> blog = Optional.ofNullable(blogService.getBlog(userDetailsImpl.getUser().getUserId()));
		
		if(blog.isPresent()) {
			Optional<Category> category = Optional.ofNullable(categoryService.searchCategoryBlogIdAndCategoryId(blog.get().getBlogId(), id));
			
			if(category.isPresent()) {
				categoryService.deleteCategory(category.get());
				return "카테고리 삭제가 완료되었습니다.";
			}
		}
		return "카테고리 삭제가 실패하였습니다.";
	}
	
}
