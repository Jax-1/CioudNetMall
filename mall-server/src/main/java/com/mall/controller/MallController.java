package com.mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/")
public class MallController {
	 @Autowired
	 RedisConnectionFactory factory;
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
