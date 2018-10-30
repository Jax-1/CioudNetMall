package com.mall.api.login;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.api.BaseAPI;
import com.mall.entity.login.User;
import com.mall.message.ProcessResult;
import com.mall.message.SystemCode;
import com.mall.service.login.UserLoginService;
import com.mall.service.login.impl.UserLoginUtil;
import com.mall.util.DateFormatUtil;
import com.mall.util.MD5Util;
import com.mall.util.Validate;

/**
 * 用户API
 * @author Jang
 *
 */
@Controller
@RequestMapping("api/user")
public class LoginApi extends BaseAPI {
	@Resource
	private UserLoginService userLoginService;
	@Resource
	private UserLoginUtil userLoginUtil;
	
	/**
	 * 登录
	 * @return
	 */
	@PostMapping("/login")
	@ResponseBody
	public ProcessResult<User> toLogin(User user, HttpServletRequest request){
		logger.info(request.getServletPath() );
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
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ProcessResult<User> registered(User user,String imgcode,String phonecode,HttpServletRequest req){
		/**
		 * 校验验证信息
		 */
		ProcessResult process=new ProcessResult();
		if(!Validate.notNull(imgcode)||!Validate.notNull(phonecode)) {
			process.setMsg("验证码不得为空！");
			return process;
		}
		// 验证图片验证码
        String sessionCode = req.getSession().getAttribute("imgcode").toString();
        if (Validate.notNull(imgcode)&&Validate.notNull(sessionCode)) {
            if (!imgcode.equalsIgnoreCase(sessionCode)) {
            	process.setMsg("验证失败！");
            	return process;
            } 
        }else {
        	process.setMsg("验证失败！");
        	return process;
        } 
        Object obj =req.getSession().getAttribute("phonecode");
        if(!Validate.notNull(obj)) {
        	 logger.info("用户注册，验证码获取失败！");
        	 process.setMsg("用户注册，验证码获取失败！");
        	 return process;
        }
        //验证手机验证码
        String sessionPhoneCode = req.getSession().getAttribute("phonecode").toString();
        if (Validate.notNull(phonecode)&&Validate.notNull(sessionPhoneCode)) {
            if (!phonecode.equals(sessionPhoneCode)) {
            	process.setMsg("手机验证失败！");
            	return process;
            } 
        }else {
        	process.setMsg("手机验证失败！");
        	return process;
        } 
        logger.info("用户注册，验证码校验成功！");
		/**
		 * 用户信息处理
		 */
		if(!Validate.notNull(user)||!Validate.notNull(user.getUser_name())||!Validate.notNull(user.getPassword())) {
			return new ProcessResult<User>(SystemCode.FAILURE,"用户信息错误！");
		}
		User usermodel = new User();
		String rand = MD5Util.getRand();
		usermodel.setUser_name(user.getUser_name());
		usermodel.setCreate_at(DateFormatUtil.getDate());
		//加密，密码+秘钥
		usermodel.setPassword(MD5Util.encoder(user.getPassword(), rand));
		usermodel.setRand(rand);
		usermodel.setStatus(SystemCode.STATUS_Y);
		return userLoginService.registered(usermodel);
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
