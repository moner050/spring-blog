package com.fastcampus.web.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fastcampus.biz.service.BlogService;

@Controller
public class WelcomeController {
	
	@Autowired
	private BlogService blogService;

	public WelcomeController(BlogService blogService) {
		this.blogService = blogService;
	}

	@GetMapping({"", "/"})
	public String welcome(HttpSession session
			, @PageableDefault(size = 5, sort = "blogId", direction = Direction.DESC) Pageable pageable
			) {
		if(session.getAttribute("searchKeyword") == null) {
			session.setAttribute("blogList", blogService.getBlogList(pageable));
		}
		else {
			String searchCondition = (String) session.getAttribute("searchCondition");
			String searchKeyword = (String) session.getAttribute("searchKeyword");
			
			if(searchCondition.equals("TITLE")) {
				session.setAttribute("blogList", blogService.getBlogByTitle(pageable, searchKeyword));
			}
			else if(searchCondition.equals("TAG")) {
				session.setAttribute("blogList", blogService.getBlogByTag(pageable, searchKeyword));
			}
		}
		return "welcome";
	}
	
	// 블로그 검색
	@GetMapping("/search")
	@ResponseBody
	public String searchBlog(String searchCondition, String searchKeyword, HttpSession session,
			@PageableDefault(size = 5, sort = "blogId", direction = Direction.DESC) Pageable pageable
			){
		session.setAttribute("searchCondition", searchCondition);
		session.setAttribute("searchKeyword", searchKeyword);

		session.removeAttribute("blogList");

		if(searchCondition.equals("TITLE")) {
			session.setAttribute("blogList", blogService.getBlogByTitle(pageable, searchKeyword));
		}
		else if(searchCondition.equals("TAG")) {
			session.setAttribute("blogList", blogService.getBlogByTag(pageable, searchKeyword));
		}
		
		return "검색이 완료되었습니다.";
	}
	
	@GetMapping("/reset")
	public String reset(HttpSession session
			, @PageableDefault(size = 5, sort = "blogId", direction = Direction.DESC) Pageable pageable
			) {
		session.removeAttribute("blogList");
		session.removeAttribute("searchCondition");
		session.removeAttribute("searchKeyword");
		
		session.setAttribute("blogList", blogService.getBlogList(pageable));

		return "redirect:/";
	}

}
