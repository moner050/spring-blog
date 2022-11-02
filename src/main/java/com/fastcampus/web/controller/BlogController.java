package com.fastcampus.web.controller;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fastcampus.biz.domain.Blog;
import com.fastcampus.biz.domain.BlogUser;
import com.fastcampus.biz.domain.Category;
import com.fastcampus.biz.domain.Post;
import com.fastcampus.biz.dto.BlogDto;
import com.fastcampus.biz.service.BlogService;
import com.fastcampus.biz.service.CategoryService;
import com.fastcampus.biz.service.PostService;
import com.fastcampus.security.jpa.UserDetailsImpl;

@Controller
public class BlogController {

	private BlogService blogService;
	private PostService postService;
	private CategoryService categoryService;
	
	@Autowired
	public BlogController(BlogService blogService, PostService postService, CategoryService categoryService) {
		this.blogService = blogService;
		this.postService = postService;
		this.categoryService = categoryService;
	}
	
	// 블로그 등록 페이지 가기
	@GetMapping("/blog/insert")
	public String getInsertBlog() {
		return "blog/insertBlog";
	}

	// 블로그 등록
	@PostMapping("/blog/insert")
	@ResponseBody
	public String insertBlog(@RequestParam("title") String title, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {		
		Optional<BlogUser> user = Optional.ofNullable(userDetailsImpl.getUser());
		
		// 인증정보가 없으면 메인페이지로 이동
		if(user.isPresent()){
			Optional<Blog> emptyBlog = Optional.ofNullable(new Blog());
			Optional<Blog> tempBlog = Optional.ofNullable(blogService.getBlog(user.get().getUserId()));
			
			if(tempBlog.get().getBlogId() != emptyBlog.get().getBlogId()) {
				return "이미 블로그를 보유하고 계십니다.";
			}
			
			Blog blog = Blog.builder()
					.blogId(user.get().getUserId())
					.title(title)
					.build();
			
			blog.joinCategories(new Category());
			
			blogService.insertBlog(blog);
			
			return "블로그 등록에 성공하셨습니다";
		}
		return "블로그 등록에 실패하셨습니다.";
	}
	
	// 블로그 조회
	@GetMapping("/blog/{id}")
	public String getBlog(@PathVariable("id") Long id, Model model,
			@PageableDefault(size = 2, sort = "postId", direction = Direction.DESC) Pageable postPageable) {
		Optional<Blog> blog = Optional.ofNullable(blogService.getBlog(id));
		Optional<Page<Post>> post = Optional.ofNullable(postService.getPostList(postPageable, id));
		Optional<ArrayList<Category>> category = Optional.ofNullable(categoryService.getCategoryListBlogId(blog.get().getBlogId()));
			
		// 블로그 조회 정보가 없다면 메인페이지로 이동
		if(blog.isPresent()) {
			if(post.isPresent()){
				model.addAttribute("postList", post.get());
			}
			
			if(category.isPresent()) {
				model.addAttribute("categoryList", category.get());
			}
			model.addAttribute("blog", blog.get());
			return "blog/blogMain";
		}
		return "welcome";
	}
	
	// 내 블로그 조회
	@GetMapping("/blog")
	public String getMyBlog(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, Model model,
			@PageableDefault(size = 2, sort = "postId", direction = Direction.DESC) Pageable postPageable) {
		Optional<Blog> blog = Optional.ofNullable(blogService.getBlog(userDetailsImpl.getUser().getUserId()));
		Optional<Page<Post>> post = Optional.ofNullable(postService.getPostList(postPageable, userDetailsImpl.getUser().getUserId()));
		Optional<ArrayList<Category>> category = Optional.ofNullable(categoryService.getCategoryListBlogId(blog.get().getBlogId()));
		
		// 블로그 조회 정보가 없다면 메인페이지로 이동
		if(blog.isPresent()) {
			if(post.isPresent()){
				model.addAttribute("postList", post.get());
			}

			if(category.isPresent()) {
				model.addAttribute("categoryList", category.get());
			}
			
			model.addAttribute("blog", blog.get());
			return "blog/blogMain";
		}
		return "welcome";
	}
	
	// 블로그 포스트 검색
	@GetMapping("/blog/{id}/search/{categoryId}")
	public String searchBlogCategory(@PathVariable("id") Long id, @PathVariable("categoryId") Long categoryId,  Model model,
			@PageableDefault(size = 2, sort = "postId", direction = Direction.DESC) Pageable postPageable) {
		Optional<Blog> blog = Optional.ofNullable(blogService.getBlog(id));
		Optional<Page<Post>> post = Optional.ofNullable(postService.searchPost(postPageable, id, categoryId));
		Optional<ArrayList<Category>> category = Optional.ofNullable(categoryService.getCategoryListBlogId(blog.get().getBlogId()));
		
		// 블로그 조회 정보가 없다면 메인페이지로 이동
		if(blog.isPresent()) {
			if(post.isPresent()){
				model.addAttribute("postList", post.get());
			}
			
			if(category.isPresent()) {
				model.addAttribute("categoryList", category.get());
			}
			model.addAttribute("blog", blog.get());
			return "blog/blogMain";
		}
		return "welcome";
	}
	
	// 블로그 관리 페이지 가기
	@GetMapping("/blog/setting")
	public String getMyBlogSetting(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, Model model) {
		Optional<Blog> blog = Optional.ofNullable(blogService.getBlog(userDetailsImpl.getUser().getUserId()));
		
		// 블로그 조회 정보가 없다면 메인페이지로 이동
		if(blog.isPresent()) {
			model.addAttribute("blog", blog.get());
			return "blog/getBlog";
		}
		return "welcome";
	}
	
	// 블로그 제목 및 태그 수정
	@ResponseBody
	@PostMapping("/blog/update")
	public String updateBlog(@RequestBody BlogDto myBlog, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
		Optional<BlogUser> user = Optional.ofNullable(userDetailsImpl.getUser());
		
		// 인증정보가 없으면 메인페이지로 이동
		if(user.isPresent()){
			
			Blog blog = blogService.getBlog(user.get().getUserId());
			blog = Blog.builder()
					.blogId(user.get().getUserId())
					.title(myBlog.getTitle())
					.tag(myBlog.getTag())
					.build();
			
			blogService.updateBlog(blog);
			
			return "블로그 수정에 성공하셨습니다.";
		}
		return "블로그 수정에 실패하셨습니다.";
	}
	
	// 블로그 삭제 페이지
	@GetMapping("/blog/delete")
	public String getDeleteBlog(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, Model model) {
		Optional<Blog> blog = Optional.ofNullable(blogService.getBlog(userDetailsImpl.getUser().getUserId()));
		
		// 블로그 조회 정보가 없다면 메인페이지로 이동
		if(blog.isPresent()) {
			model.addAttribute("blog", blog.get());
			return "blog/deleteRequest";
		}
		return "welcome";
	}
	
	// 블로그 삭제요청
	@PostMapping("/blog/delete/{id}")
	public String deleteBlog(@PathVariable("id") Long id) {

		Blog myBlog = blogService.getBlog(id);
		
		Blog blog = new Blog();
		blog = Blog.builder()
				.blogId(id)
				.title(myBlog.getTitle())
				.tag(myBlog.getTag())
				.status("삭제요청")
				.build();
		
		blogService.updateBlog(blog);

		return "welcome";
	}
	
	// 블로그 삭제하기
	@DeleteMapping("/delete/{id}")
	@ResponseBody
	public String deleteBlogAdmin(@PathVariable("id") Long id){
		Optional<Blog> blog = Optional.ofNullable(blogService.getBlog(id));
		
		if(blog.isPresent()) {
			blogService.deleteBlog(blog.get());
			return "삭제가 완료되었습니다.";
		}
		return "삭제가 완료되지 않았습니다";
	}
}
