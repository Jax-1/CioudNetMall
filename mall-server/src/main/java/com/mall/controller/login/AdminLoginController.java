package com.mall.controller.login;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.code.ProcessResult;
import com.mall.controller.AbstractController;
import com.mall.entity.login.Admin;
import com.mall.service.login.AdminLoginService;

@Controller
@RequestMapping("/admin")
public class AdminLoginController extends AbstractController{
	@Resource
	private AdminLoginService adminLoginService;
	@RequestMapping("/login")
	public String toLogin(Admin admin,HttpServletRequest request) {
		return null;
	}
	@ResponseBody
	@RequestMapping("/logout")
	public ProcessResult<Admin> loginOut(){
		return null;
	}
	@RequestMapping("/registered")
	public String registered(Admin admin) {
		return null;
	}
	
}
