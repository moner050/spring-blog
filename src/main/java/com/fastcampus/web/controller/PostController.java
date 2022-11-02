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
import com.fastcampus.biz.domain.Post;
import com.fastcampus.biz.dto.PostDto;
import com.fastcampus.biz.service.BlogService;
import com.fastcampus.biz.service.CategoryService;
import com.fastcampus.biz.service.PostService;
import com.fastcampus.security.jpa.UserDetailsImpl;

@Controller
public class PostController {

	private BlogService blogService;
	private PostService postService;
	private CategoryService categoryService;

	@Autowired
	public PostController(PostService postService, BlogService blogService, CategoryService categoryService) {
		this.blogService = blogService;
		this.postService = postService;
		this.categoryService = categoryService;
	}
	
	// 포스트 작성 페이지 가기
	@GetMapping("/post/insert")
	public String getPostInsert(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, Model model,
			@PageableDefault(size = 3, sort = "categoryId", direction = Direction.DESC) Pageable pageable) {
		Optional<Blog> blog = Optional.ofNullable(blogService.getBlog(userDetailsImpl.getUser().getUserId()));
		// 로그인 상태가 아니면 메인으로 이동
		if(blog.isPresent()) {
			Optional<Page<Category>> categories = Optional.ofNullable(categoryService.getCategoryListBlogId(pageable, blog.get().getBlogId()));
			if(categories.isPresent()) {
				model.addAttribute("categoryList", categories.get());
				model.addAttribute("blog", blog.get());
				return "post/insertPost";
			}
		}
		return "welcome";
	}
	
	// 포스트 작성하기
	@PostMapping("/post/insert")
	@ResponseBody
	public String postInsert(@RequestBody PostDto post, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl, Model model,
			@PageableDefault(size = 100, sort = "categoryId", direction = Direction.DESC) Pageable pageable){
		Optional<Blog> blog = Optional.ofNullable(blogService.getBlog(userDetailsImpl.getUser().getUserId()));
		
		// 블로그가 없으면 메인으로 이동
		if(blog.isPresent()) {
			Optional<Category> category = Optional.ofNullable(categoryService.getCategoryBlogIdAndCategoryName(blog.get().getBlogId(), post.getCategoryName()));
			
			if(category.isPresent()) {
				
				Post myPost = Post.builder()
						.title(post.getTitle())
						.content(post.getContent())
						.build();
				
				myPost.joinBlog(blog.get());
				myPost.joinCategory(category.get());
				
				postService.insertPost(myPost);

				return "글 작성이 완료되었습니다.";
			}
		}
		return "글 작성을 실패했습니다.";
	}
	
	// 포스트 수정페이지 가기
	@GetMapping("/post/update/{id}")
	public String getPostUpdate(@PathVariable("id") Long id, Model model, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
			@PageableDefault(size = 100, sort = "categoryId", direction = Direction.DESC) Pageable pageable) {
		Optional<Blog> blog = Optional.ofNullable(blogService.getBlog(userDetailsImpl.getUser().getUserId()));
		Optional<Post> post = Optional.ofNullable(postService.getPost(id));
		
		// 게시글이 삭제된 상태라면 블로그 메인으로 이동
		if(post.isPresent()) {
			Optional<Page<Category>> categories = Optional.ofNullable(categoryService.getCategoryListBlogId(pageable, blog.get().getBlogId()));
			if(categories.isPresent()) {
				
				model.addAttribute("blog", blog.get());
				model.addAttribute("categoryList", categories.get());
				model.addAttribute("post", post.get());
				return "post/getPost";
			}
		}
		model.addAttribute("blog", blog.get());
		return "blog/blogMain";
	}
	
	// 포스트 수정하기
	@PostMapping("/post/update/{id}")
	@ResponseBody
	public String postUpdate(@PathVariable("id") Long id, Model model, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
			@RequestBody PostDto postDto) {
		Optional<Blog> blog = Optional.ofNullable(blogService.getBlog(userDetailsImpl.getUser().getUserId()));
		
		if(blog.isPresent()) {
			Optional<Post> post = Optional.ofNullable(postService.getPost(id));
			
			if(post.isPresent()) {
				Optional<Category> category = Optional.ofNullable(categoryService.getCategoryBlogIdAndCategoryName(blog.get().getBlogId(), postDto.getCategoryName()));
				
				if(category.isPresent()) {
					Post myPost = post.get();

					myPost.setTitle(postDto.getTitle());
					myPost.setContent(postDto.getContent());
					
					myPost.joinBlog(blog.get());
					myPost.joinCategory(category.get());
					
					postService.insertPost(myPost);
					return "수정에 성공하였습니다.";
				}
			}
		}
		return "수정에 실패했습니다.";
	}
	
	// 포스트 삭제
	@DeleteMapping("/post/delete/{id}")
	@ResponseBody
	public String deletePost(@PathVariable("id") Long id, Model model, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
		Optional<Post> post = Optional.ofNullable(postService.getPost(id));
		Optional<Blog> blog = Optional.ofNullable(blogService.getBlog(userDetailsImpl.getUser().getUserId()));
		
		// 게시글이 존재하면 게시글을 삭제.
		if(post.isPresent()) {
			Post myPost = postService.getPost(id);
			postService.deletePost(myPost);
		}
		
		// 블로그가 존재하면 블로그 메인으로 이동
		if(blog.isPresent()) {
			model.addAttribute("blog", blog.get());
			return "게시글 삭제가 완료되었습니다.";
		}
		return "게시글 삭제를 실패하였습니다.";
	}
	
}
