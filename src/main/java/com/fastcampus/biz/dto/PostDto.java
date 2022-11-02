package com.fastcampus.biz.dto;

import com.fastcampus.biz.domain.Blog;
import com.fastcampus.biz.domain.Category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PostDto {

	private Long postId;
	
	private Blog blog;
	
	private Category category;
	
	private String title;
	
	private String content;
	
	private String categoryName;
	
}
