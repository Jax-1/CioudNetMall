package com.mall.service.login.impl;

import java.util.List;

import javax.annotation.Resource;

import com.mall.dao.base.IBaseDao;
import com.mall.dao.login.UserInfoMapper;
import com.mall.entity.login.UserInfo;
import com.mall.service.BaseServiceImpl;
import com.mall.service.login.UserInfoService;

public class UserInfoServiceImpl extends BaseServiceImpl<UserInfo> implements UserInfoService {
	@Resource
	private UserInfoMapper userInfoMapper;
	@Override
	protected IBaseDao<UserInfo> getMapper() {
		return userInfoMapper;
	}

	

}
