package com.mall.service.user;

import javax.servlet.http.HttpServletRequest;

import com.mall.entity.user.Admin;
import com.mall.message.ProcessResult;

public interface AdminLoginService {
	/**
	 * 登陆
	 * @param admin
	 * @param request
	 * @return
	 */
	ProcessResult<Admin> login(Admin admin,HttpServletRequest request);
	/**
	 * 忘记密码
	 * @param admin
	 * @return
	 */
	ProcessResult<Admin> forgotPassword(Admin admin);
	/**
	 * 注销
	 * @param request
	 * @return
	 */
	ProcessResult<Admin> loginOut(HttpServletRequest request);
	/**
	 * 注册
	 * @param admin
	 * @return
	 */
	ProcessResult<Admin> registered(Admin admin);
	/**
	 * 检查用户名
	 * @return
	 */
	boolean checkAdminName(String adminName);
}
