package com.mall.controller.login;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.controller.AbstractController;
import com.mall.entity.cms.AtticleldCategory;
import com.mall.entity.login.Admin;
import com.mall.message.ProcessResult;
import com.mall.message.SystemCode;
import com.mall.service.cms.AtticleldCategoryService;
import com.mall.service.login.AdminLoginService;
import com.mall.util.DateFormatUtil;
import com.mall.util.MD5Util;
import com.mall.util.Validate;

@Controller
@RequestMapping("/admin")
public class AdminLoginController extends AbstractController{
	@Resource
	private AdminLoginService adminLoginService;
	@Resource
	private AtticleldCategoryService atticleldCategoryService;
	/**
	 * 跳转到主界面
	 * @return
	 */
	@RequestMapping("")
	public String doIndex(Model model) {
		//初始化界面信息
		//cms
		
		model.addAttribute("page", "admin/index_body");
		return "admin/index";
	}
	/**
	 * 跳转至登陆界面
	 * @return
	 */
	@RequestMapping("/login.do")
	public String doAdmin() {
		return "admin/admin_login";
	}
	/**
	 * 登陆
	 * @param admin
	 * @param request
	 * @return
	 */
	@PostMapping("/login")
	@ResponseBody
	public ProcessResult<Admin> Login(Admin admin,HttpServletRequest request) {
		if(!Validate.notNull(admin)) {
			return new ProcessResult<Admin>();
		}
		ProcessResult<Admin> result = adminLoginService.login(admin, request);
		return result;
	}
	/**
	 * 注销
	 * @param request
	 * @return
	 */
	@ResponseBody
	@PostMapping("/logout")
	public ProcessResult<Admin> loginOut(HttpServletRequest request){
		ProcessResult<Admin> loginOut = adminLoginService.loginOut(request);
		return loginOut;
	}
	/**
	 * 注册
	 * @param admin
	 * @return
	 */
	
	@PostMapping("/registered")
	@ResponseBody
	public ProcessResult<Admin> registered(Admin admin) {
		//信息为空或账号存在
		if(!Validate.notNull(admin)||!Validate.notNull(admin.getAdmin_name())||!Validate.notNull(admin.getPassword())||
				adminLoginService.checkAdminName(admin.getAdmin_name())) {
			return new ProcessResult<Admin>(SystemCode.FAILURE,"账号信息错误");
		}
		Admin adminUser = new Admin();
		adminUser.setAdmin_name(admin.getAdmin_name());
		//密码
		String rand=MD5Util.getRand();
		adminUser.setRand(rand);
		adminUser.setPassword(MD5Util.encoder(admin.getPassword(), rand));
		adminUser.setCreate_time(DateFormatUtil.getDate());
		adminUser.setState(SystemCode.STATUS_Y);
		adminUser.setDescription("超级管理员");
		ProcessResult<Admin> result = adminLoginService.registered(adminUser);
		return result;
	}
	/**
	 * 检查用户名
	 * @param adminname
	 * @return
	 */
	@PostMapping("/checkName")
	@ResponseBody
	public ProcessResult<Admin> ckeckAdminName(String adminname){
		if(Validate.notNull(adminname)) {
			return (adminLoginService.checkAdminName(adminname))?(new ProcessResult<Admin>(SystemCode.FAILURE,"账号重复！")):(new ProcessResult<Admin>(SystemCode.SUCCESS,"账号可用！"));
		}
		return null;
	}
	
}
