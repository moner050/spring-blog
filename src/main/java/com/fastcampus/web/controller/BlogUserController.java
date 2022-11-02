package com.fastcampus.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BlogUserController {

	// 로그인 페이지 이동하기
	@GetMapping("/auth/login")
	public String login() {
		return "system/login";
	}

}
