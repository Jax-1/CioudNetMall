package com.mall.conf;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.mall.message.SystemCode;
import com.mall.service.sys.CacheService;
import com.mall.util.Validate;

/**
 * 初始化连接
 * @author Jang
 *
 */
public class InitializeConnection implements HandlerInterceptor{
	protected  Logger logger = Logger.getLogger(this.getClass());
	@Resource
	private CacheService cacheService;
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		logger.info("初始化连接信息！");
		//文件服务器路径
		if(!Validate.notNull(request.getSession().getAttribute("fileServicePath"))) {
			logger.info("加载文件访问地址信息！");
			Map<String, String> cache = cacheService.getCache(SystemCode.FILE_SERVICE);
			String url=cache.get(SystemCode.FILE_SERVICE_URL);
			String port=cache.get(SystemCode.FILE_SERVICE_PORT);
			String filePath=cache.get(SystemCode.FILE_SERVICE_FILES_PATH);
			String fileUrlPrefix=url+":"+port+"/"+filePath;
			request.getSession().setAttribute("fileServicePath", fileUrlPrefix);
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
