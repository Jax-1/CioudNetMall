package com.mall.service.login.impl;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mall.dao.base.IBaseDao;
import com.mall.dao.login.UserInfoMapper;
import com.mall.dao.login.UserMapper;
import com.mall.entity.login.User;
import com.mall.entity.login.UserInfo;
import com.mall.message.MessageUtil;
import com.mall.message.MsgPoolCode;
import com.mall.message.ProcessResult;
import com.mall.message.SystemCode;
import com.mall.service.BaseServiceImpl;
import com.mall.service.login.UserLoginService;
import com.mall.util.DateFormatUtil;
import com.mall.util.MD5Util;
import com.mall.util.SessionUtil;
import com.mall.util.UUIDUtil;
import com.mall.util.Validate;
@Service
public class UserLoginServiceImpl extends BaseServiceImpl<User> implements UserLoginService {
	@Resource
	private UserMapper userMapper;
	@Resource
	private UserInfoMapper userInfoMapper;

	@Override
	public ProcessResult<User> login(User user, HttpServletRequest request) {
		ProcessResult<User> result = new ProcessResult<User>();
		User model = userMapper.Login(user);
		//状态验证
		if(model==null||SystemCode.STATUS_N.equals(model.getStatus())) {
			result.setMsg(MessageUtil.getMsgByLan(MsgPoolCode.USER_STATUS_N_MSG));
			return result;
		}
		if(Validate.notNull(model)) {
			//密码验证
			String password = MD5Util.encoder(user.getPassword(),model.getRand());
			if(password.equals(model.getPassword())) {
				SessionUtil.setUser(request, model);
				result.setRes(SystemCode.SUCCESS);
				result.setMsg(MessageUtil.getMsgByLan(MsgPoolCode.LOGIN_SUCESS));
			}else {
				result.setRes(SystemCode.FAILURE);
				result.setMsg(MessageUtil.getMsgByLan(MsgPoolCode.NO_OBJ_ERROR_PASS));
			}
			
		}else {
			result.setMsg(MessageUtil.getMsgByLan(MsgPoolCode.NO_OBJ_ERROR_PASS));
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
		logger.info("注销");
		SessionUtil.setUser(request, null);
		request.getSession().invalidate();
		return new ProcessResult<User>(SystemCode.SUCCESS,MessageUtil.getMsgByLan(MsgPoolCode.LOGIN_OUT_SUCESS));
	}



	@Override
	public ProcessResult<User> registered(User user) {
		ProcessResult<User> result = new ProcessResult<User>(SystemCode.SUCCESS,"注册成功！");
		try {
			//生成默认用户信息
			String id = UUIDUtil.getUUID();
			user.setUserinfo_id(id);
			userMapper.insert(user);
			UserInfo userInfo = UserInfo.init(user, id);
			userInfoMapper.insert(userInfo);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	protected IBaseDao<User> getMapper() {
		return userMapper;
	}

	/**
	 * 查询用户信息
	 * @param user
	 * @return
	 */
//	@Transactional
//	private UserInfo queryuserInfoFromUser(User user) {
//		UserInfo userInfo=new UserInfo();
//		if(Validate.notNull(user.getUserinfo_id())) {
//			userInfo = userInfoMapper.selectByPrimaryKey(user.getUserinfo_id());
//		}else {
//			String uuid = UUIDUtil.getUUID();
//			userInfo.setId(uuid);
//			userInfo.setUser_name("用户"+Math.random()*10000);
//			userInfo.setHeadimgurl(SystemCode.USERINFO_HEADIMGURL);
//			userInfo.setCreate_at(DateFormatUtil.getDate());
//			user.setUserinfo_id(uuid);
//			userInfoMapper.insert(userInfo);
//			userMapper.updateByPrimaryKey(user);
//			
//		}
//		return userInfo;
//	}
	
}
