package com.mall.service.login;

import javax.servlet.http.HttpServletRequest;

import com.mall.entity.login.User;
import com.mall.message.ProcessResult;
import com.mall.service.IBaseService;

public interface UserLoginService extends IBaseService<User>{
	/**
	 * 登陆
	 * @param user 用户账号信息
	 * @param request 
	 * @return 处理结果
	 */
	ProcessResult<User> login(User user,HttpServletRequest request);
	/**
	 * 忘记密码
	 * @param user 用户账号信息
	 * @return
	 */
	ProcessResult<User> forgotPassword(User user);
	/**
	 * 注销
	 * @param request
	 * @return
	 */
	ProcessResult<User> loginOut(HttpServletRequest request);
	/**
	 * 注册
	 * @param user
	 * @param userInfo
	 * @return
	 */
	ProcessResult<User> registered(User user);
}
