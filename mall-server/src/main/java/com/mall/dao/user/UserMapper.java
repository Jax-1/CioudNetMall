package com.mall.dao.user;

import java.util.List;

import com.mall.dao.base.IBaseDao;
import com.mall.entity.user.User;

public interface UserMapper  extends IBaseDao<User>{
	/**
	 * 登录
	 * @param user
	 * @return
	 */
	public User Login(User user);
	
	
	
   
}