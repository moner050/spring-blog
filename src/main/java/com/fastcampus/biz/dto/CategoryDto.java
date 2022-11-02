package com.fastcampus.biz.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CategoryDto {

	private Long categoryId;
	
	private String categoryName;
	
	private String description;
	
	private String displayType;
	
}
