package com.fastcampus.biz.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fastcampus.biz.domain.Post;
import com.fastcampus.biz.persistence.PostRepository;

@Service
@Transactional
public class PostService {

	private PostRepository postRepository;
	
	@Autowired
	public PostService(PostRepository postRepository) {
		this.postRepository = postRepository;
	}
	
	// 포스트 목록 조회
	public Page<Post> getPostList(Pageable pageable, Long id){
		return postRepository.findAllByBlogBlogId(id, pageable);
	}
	
	// 포스트 상세 조회
	public Post getPost(Long id) {
		Optional<Post> post = postRepository.findById(id);
		
		return post.orElseGet(() -> new Post());
	}
	
	// 포스트 검색 (블로그아이디, 카테고리아이디)
	public Page<Post> searchPost(Pageable pageable, Long blogId, Long categoryId) {
		return postRepository.findAllByBlogBlogIdAndCategoryCategoryId(pageable, blogId, categoryId);
	}
	
	// 포스트 작성
	public void insertPost(Post post) {
		postRepository.save(post);
	}
	
	// 포스트 삭제
	public void deletePost(Post post) {
		postRepository.delete(post);
	}
	
}
