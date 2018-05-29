package com.mall.service.login;

import javax.servlet.http.HttpServletRequest;

import com.mall.entity.login.User;
import com.mall.entity.login.UserInfo;
import com.mall.message.ProcessResult;

public interface UserLoginService {
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
	 * 查询登陆用户信息
	 * @param request
	 * @return
	 */
	UserInfo queryUserInfo(HttpServletRequest request);
	/**
	 * 查询用户信息
	 * @param user
	 * @return
	 */
	UserInfo queryUserInfo(User user);
	/**
	 * 注册
	 * @param user
	 * @param userInfo
	 * @return
	 */
	ProcessResult<User> registered(User user,UserInfo userInfo);
}
