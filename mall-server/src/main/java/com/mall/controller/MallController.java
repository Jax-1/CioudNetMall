package com.mall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mall")
public class MallController {
	/**
	 * 跳转到主页
	 * @return
	 */
	@RequestMapping("")
	public String toIndex(Model model) {
		
		model.addAttribute("page", "mall/base/index_body");
		return "mall/index";
	}
}
