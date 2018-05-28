package com.mall.service.login.impl;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.transaction.annotation.Transactional;

import com.mall.code.ProcessMassage;
import com.mall.code.ProcessResult;
import com.mall.code.SystemCode;
import com.mall.dao.login.UserInfoMapper;
import com.mall.dao.login.UserMapper;
import com.mall.entity.login.User;
import com.mall.entity.login.UserInfo;
import com.mall.service.login.UserLoginService;
import com.mall.util.DateFormatUtil;
import com.mall.util.MD5Util;
import com.mall.util.SessionUtil;
import com.mall.util.UUIDUtil;
import com.mall.util.Validate;

public class UserLoginServiceImpl implements UserLoginService {
	@Resource
	private UserMapper userMapper;
	@Resource
	private UserInfoMapper userInfoMapper;

	@Override
	public ProcessResult<User> login(User user, HttpServletRequest request) {
		ProcessResult<User> result = new ProcessResult<User>();
		User model = userMapper.selectByPrimaryKey(user.getUser_name());
		//状态验证
		if(SystemCode.STATUS_N.equals(model.getStatus())) {
			result.setMsg(SystemCode.USER_STATUS_N_MSG);
			return result;
		}
		if(Validate.notNull(model)) {
			//密码验证
			String password = MD5Util.encoder(user.getPassword(),model.getRand());
			if(password.equals(model.getPassword())) {
				SessionUtil.setUser(request, model);
				result.setRes(ProcessMassage.SUCCESS);
				result.setMsg(SystemCode.LOGIN_SUCESS);
			}else {
				result.setRes(ProcessMassage.FAILURE);
				result.setMsg(SystemCode.LOGIN_FAILURE);
			}
			
		}else {
			result.setMsg(SystemCode.LOGIN_FAILURE);
		}
		return result;
	}

	@Override
	public ProcessResult<User> forgotPassword(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProcessResult<User> loginOut(HttpServletRequest request) {
		request.getSession().invalidate();
		return new ProcessResult<User>(ProcessMassage.SUCCESS,SystemCode.LOGIN_OUT_SUCESS);
	}

	@Override

	public UserInfo queryUserInfo(HttpServletRequest request) {
		User user = SessionUtil.getUser(request);
		return queryuserInfoFromUser(user);
	}

	@Override
	public ProcessResult<User> registered(User user, UserInfo userInfo) {
		ProcessResult<User> result = new ProcessResult<User>();
		userMapper.insert(user);
		userInfoMapper.insert(userInfo);
		return result;
	}

	@Override
	public UserInfo queryUserInfo(User user) {
		
		return queryuserInfoFromUser(user);
	}
	/**
	 * 查询用户信息
	 * @param user
	 * @return
	 */
	@Transactional
	private UserInfo queryuserInfoFromUser(User user) {
		UserInfo userInfo=new UserInfo();
		if(Validate.notNull(user.getUserinfo_id())) {
			userInfo = userInfoMapper.selectByPrimaryKey(user.getUserinfo_id());
		}else {
			String uuid = UUIDUtil.getUUID();
			userInfo.setId(uuid);
			userInfo.setUser_name("用户"+Math.random()*10000);
			userInfo.setHeadimgurl(SystemCode.USERINFO_HEADIMGURL);
			userInfo.setCreate_at(DateFormatUtil.getDate());
			user.setUserinfo_id(uuid);
			userInfoMapper.insert(userInfo);
			userMapper.updateByPrimaryKey(user);
			
		}
		return userInfo;
	}
	
}
