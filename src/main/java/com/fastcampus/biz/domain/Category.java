package com.fastcampus.biz.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long categoryId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "blogId")
	private Blog blog;
	
	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
	private List<Post> posts = new ArrayList<>();
	
	private String categoryName;
	
	private String description;
	
	@Convert(converter = DisplayTypeConverter.class)
	private DisplayType displayType;
	
	public void setBlog(Blog blog) {
		
		if(this.blog != null) {
			this.blog.getCategories().remove(this);
		}
		this.blog = blog;
	}
	
	public void addPost(Post post) {
		this.posts.add(post);
	}

	public Category() {
		this.categoryName = "미분류";
		this.description = "기본으로 제공되는 카테고리 입니다.";
		this.displayType = DisplayType.ALL;
	}
	
	@Builder
	public Category(String categoryName, String description, DisplayType displayType, Long categoryId) {
		this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.description = description;
        this.displayType = displayType;
	}

}
