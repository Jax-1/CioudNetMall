package com.mall.conf;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.mall.entity.login.Admin;
import com.mall.entity.login.User;
import com.mall.util.SessionUtil;
import com.mall.util.Validate;

/**
 * 拦截器
 * @author Jang
 *
 */
public class AdminLoginValidate implements HandlerInterceptor {
	protected  Logger logger = Logger.getLogger(this.getClass());
	
	/** 
     * 进入controller层之前拦截请求 
     * @param httpServletRequest 
     * @param httpServletResponse 
     * @param o 
     * @return 
     * @throws Exception 
     */ 
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		Admin admin = SessionUtil.getAdminUser(request);
		if(!Validate.notNull(admin)) {
			//未登录
			logger.info("当前管理员未登录，跳转到登录界面！");
			response.sendRedirect(request.getContextPath()+"/admin/login.do");
			return false;
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}

}
