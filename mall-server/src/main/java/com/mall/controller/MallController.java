package com.mall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MallController {
	/**
	 * 跳转到主页
	 * @return
	 */
	@RequestMapping("/")
	public String toIndex(Model model) {
		return "index";
	}
	@RequestMapping("/mall/about")
	public String toAbout(Model model) {
		
		model.addAttribute("page", "mall/about/about");
		return "mall/index";
	}
	
}
