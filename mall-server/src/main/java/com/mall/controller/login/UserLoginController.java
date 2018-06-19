package com.mall.controller.login;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.entity.login.Admin;
import com.mall.entity.login.User;
import com.mall.entity.login.UserInfo;
import com.mall.message.ProcessResult;
import com.mall.message.SystemCode;
import com.mall.service.login.UserLoginService;
import com.mall.service.login.impl.UserLoginUtil;
import com.mall.util.DateFormatUtil;
import com.mall.util.MD5Util;
import com.mall.util.UUIDUtil;
import com.mall.util.Validate;

import com.mall.service.sys.CacheService;

@Controller
@RequestMapping("/mall")
public class UserLoginController {
	@Resource
	private UserLoginService userLoginService;
	@Resource
	private UserLoginUtil userLoginUtil;
	@Resource
	private CacheService cacheService;
	/**
	 * 跳转到主页
	 * @return
	 */
	@RequestMapping("")
	public String toIndex(Model model) {
		cacheService.getCache(SystemCode.PAGE);
		
		model.addAttribute("page", "/mall/base/index_body");
		return "/mall/index";
	}
	/**
	 * 跳转到登录界面
	 * @return
	 */
	@RequestMapping("/login.do")
	public String toLogin(Model model) {
		 
		model.addAttribute("page", "/mall/login/login");
		return "/mall/index";
	}
	/**
	 * 跳转到注册界面
	 * @param model
	 * @return
	 */
	@RequestMapping("/register.do")
	public String toRegister(Model model) {
		 
		model.addAttribute("page", "/mall/login/register");
		return "/mall/index";
	}
	
	/**
	 * 登录
	 * @return
	 */
	@PostMapping("/login")
	@ResponseBody
	public ProcessResult<User> toLogin(User user, HttpServletRequest request){
		if(!Validate.notNull(user)||!Validate.notNull(user.getUser_name())||!Validate.notNull(user.getPassword())) {
			return  new ProcessResult<User>();
		}
		return userLoginService.login(user, request);
	}
	/**
	 * 检查用户名
	 * @return
	 */
	@PostMapping("/checkname")
	@ResponseBody
	public ProcessResult<User> checkName(String username){
		ProcessResult<User> result = new ProcessResult<User>();
		if(userLoginUtil.checkUserName(username)) {
			result.setMsg("用户名已存在！");
			return result;
		}
		result.setRes(SystemCode.SUCCESS);
		result.setMsg("有效的用户名");
		return result;
	}
	/**
	 * 注册
	 * @return
	 */
	@PostMapping("/register")
	@ResponseBody
	public ProcessResult<User> registered(User user,UserInfo userInfo){
		/**
		 * 用户信息处理
		 */
		if(!Validate.notNull(user)||!Validate.notNull(user.getUser_name())||!Validate.notNull(user.getPassword())) {
			return new ProcessResult<User>(SystemCode.FAILURE,"用户信息错误！");
		}
		User usermodel = new User();
		String userinfouuid = UUIDUtil.getUUID();
		String rand = MD5Util.getRand();
		usermodel.setUser_name(user.getUser_name());
		usermodel.setCreate_at(DateFormatUtil.getDate());
		//加密，密码+秘钥
		usermodel.setPassword(MD5Util.encoder(user.getPassword(), rand));
		usermodel.setRand(rand);
		usermodel.setStatus(SystemCode.STATUS_Y);
		usermodel.setUserinfo_id(userinfouuid);
		//用户信息
		userInfo.setId(userinfouuid);
		userInfo.setCreate_at(DateFormatUtil.getDate());
		return userLoginService.registered(usermodel, userInfo);
	}
	/**
	 * 注销
	 * @param request
	 * @return
	 */
	@PostMapping("/loginout")
	@ResponseBody
	public ProcessResult<User> loginOut(HttpServletRequest request){
		
		return userLoginService.loginOut(request);
	}
	
 	
}
