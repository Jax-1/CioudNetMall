package com.mall.api.login;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.api.BaseAPI;
import com.mall.dao.login.UserMapper;
import com.mall.entity.login.User;
import com.mall.entity.login.UserInfo;
import com.mall.message.ProcessResult;
import com.mall.message.SystemCode;
import com.mall.service.login.UserInfoService;
import com.mall.service.login.UserLoginService;
import com.mall.util.DateFormatUtil;
import com.mall.util.MD5Util;
import com.mall.util.SessionUtil;
import com.mall.util.Validate;

@Controller
@RequestMapping("api/user/manager")
public class UserManagerApi extends BaseAPI {
	@Resource
	private UserLoginService userLoginService;
	@Resource
	private UserInfoService userInfoService;
	@Resource
	private UserMapper userMapper;
	/**
	 * 校验验证信息
	 * @param model
	 * @param request
	 * @param imgcode
	 * @param phonecode
	 * @return
	 */
	@RequestMapping("/checkCode")
	@ResponseBody
	public ProcessResult<User> checkCode(Model model,HttpServletRequest request,String imgcode,String phonecode) {
		/**
		 * 校验验证信息
		 */
		ProcessResult<User> process=new ProcessResult<User>();
		if(!Validate.notNull(imgcode)||!Validate.notNull(phonecode)) {
			process.setMsg("验证码不得为空！");
			return process;
		}
		// 验证图片验证码
        String sessionCode = request.getSession().getAttribute("imgcode").toString();
        if (Validate.notNull(imgcode)&&Validate.notNull(sessionCode)) {
            if (!imgcode.equalsIgnoreCase(sessionCode)) {
            	process.setMsg("验证失败！");
            	return process;
            } 
        }else {
        	process.setMsg("验证失败！");
        	return process;
        } 
        //验证手机验证码
        String sessionPhoneCode = request.getSession().getAttribute("phonecode").toString();
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
        //将校验结果存入session
        request.getSession().setAttribute("checkCode", true);
        process.setRes(SystemCode.SUCCESS);
        return process;
	}
	/**
	 * 更改密码
	 * @param model
	 * @param request
	 * @param password
	 * @return
	 */
	@RequestMapping("/modify")
	@ResponseBody
	public Boolean toChengePwd(HttpServletRequest request,String password) {
		logger.info("用户更改密码校验："+request.getSession().getAttribute("checkCode"));
		if(request.getSession().getAttribute("checkCode")==null||
				!Boolean.valueOf(request.getSession().getAttribute("checkCode").toString()).booleanValue()) {
			//未经验证
			return false;
			
		}
		//更改密码
		User user = SessionUtil.getUser(request);
		String rand = MD5Util.getRand();
		user.setRand(rand);
		user.setPassword(MD5Util.encoder(password,rand));
		try {
			userLoginService.updateByPrimaryKeySelective(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//清除登陆信息
		SessionUtil.setUser(request, null);
		return true;
	}
	/**
	 * 更改用户信息
	 * @param model
	 * @param request
	 * @param userinfo
	 * @return
	 */
	@RequestMapping("/chengeuserinfo")
	@ResponseBody
	public ProcessResult<User> chengeUserInfo(HttpServletRequest request,UserInfo userInfo) {
		ProcessResult<User> res=new ProcessResult<User>();
		try {
			
			/**
			 * 重新写入用户信息
			 */
			
			User user = SessionUtil.getUser(request);
			userInfo.setUpdate_time(DateFormatUtil.getDate());
			user.setUserInfo(userInfo);
			userInfo.setId(user.getUserinfo_id());
			userInfo.setUser_name(user.getUser_name());
			userInfo.setCreate_at(user.getUserInfo().getCreate_at());
			logger.info("更改用户信息："+userInfo.getUser_name());
			
			userInfoService.updateByPrimaryKey(userInfo);
			user=userMapper.Login(user);
			SessionUtil.setUser(request, user);
			res.setRes(SystemCode.SUCCESS);
			res.setMsg("用户信息修改成功");
		} catch (Exception e) {
			//更新用户信息失败
			return res;
		}
		
		return res;
	}

}
