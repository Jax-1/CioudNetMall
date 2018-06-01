package com.mall.controller.login;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.entity.login.User;
import com.mall.message.ProcessResult;

@Controller
@RequestMapping("/mall")
public class UserLoginController {
	
	/**
	 * 登录
	 * @return
	 */
	@PostMapping("/login")
	@ResponseBody
	public ProcessResult<User> toLogin(){
		return null;
	}
	/**
	 * 检查用户名
	 * @return
	 */
	@PostMapping("/checkname")
	@ResponseBody
	public ProcessResult<User> checkName(){
		return null;
	}
	/**
	 * 注册
	 * @return
	 */
	@PostMapping("/register")
	@ResponseBody
	public ProcessResult<User> registered(){
		return null;
	}
	
 	
}
