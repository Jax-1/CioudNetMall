package com.mall.service.user.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mall.dao.base.IBaseDao;
import com.mall.dao.user.UserInfoMapper;
import com.mall.entity.user.UserInfo;
import com.mall.service.BaseServiceImpl;
import com.mall.service.user.UserInfoService;


@Service
public class UserInfoServiceImpl extends BaseServiceImpl<UserInfo> implements UserInfoService {
	@Resource
	private UserInfoMapper userInfoMapper;
	@Override
	protected IBaseDao<UserInfo> getMapper() {
		return userInfoMapper;
	}

	

}
