package com.mall.service.login.impl;

import javax.annotation.Resource;

import com.mall.dao.login.UserInfoMapper;
import com.mall.dao.login.UserMapper;
import com.mall.entity.login.User;
import com.mall.util.Validate;

public class UserLoginUtil {
	@Resource
	private UserMapper userMapper;
	@Resource
	private UserInfoMapper userInfoMapper;
	/**
	 * 校验用户
	 * @param user_name
	 * @return 用户存在返回true
	 */
	private boolean checkUserName(String user_name) {
		User user = userMapper.selectByPrimaryKey(user_name);
		if(Validate.notNull(user)) {
			return true;
		}
		return false;
	}

}
