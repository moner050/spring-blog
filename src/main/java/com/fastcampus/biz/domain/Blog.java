package com.fastcampus.biz.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Blog {
	
	@Id
	@Column(nullable = false)
	private Long blogId;
	
	private String status;
	
	private String tag;
	
	private String title;
	
	// insert 할 때 status나 tag 에 기본값이 들어가게 설정
	@PrePersist
	public void prePersist() {
		this.status = "운영";
		this.tag = "No Tag";
	}
	
	// update 할 때 status나 tag 에 null 값이 들어가면 기본값이 들어가게 설정
	@PreUpdate
	public void preUpdate() {
		this.status = this.status == null ? "운영" : this.status;
		this.tag = this.tag == null ? "No Tag" : this.tag;
	}
	
	@OneToMany(mappedBy = "blog", cascade = CascadeType.ALL)
	private List<Category> categories = new ArrayList<>();
	
	
	@OneToMany(mappedBy = "blog", cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();
	
	@Builder
	public Blog(Long blogId, String status, String tag, String title) {
		this.blogId = blogId;
		this.status = status;
		this.tag = tag;
		this.title = title;
	}

	public void joinCategories(Category category) {
		this.categories.add(category);
		if(category.getBlog() != this) {
			category.setBlog(this);
		}
	}
	
	public void addPost(Post post) {
		this.posts.add(post);
		if(post.getBlog() != this) {
			post.joinBlog(this);
		}
	}
	
}
