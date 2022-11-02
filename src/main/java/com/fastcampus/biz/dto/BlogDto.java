package com.fastcampus.biz.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BlogDto {

	private Long blogId;
	
	private String status;
	
	private String tag;
	
	private String title;

}
