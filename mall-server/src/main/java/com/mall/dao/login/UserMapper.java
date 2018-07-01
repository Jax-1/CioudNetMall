package com.mall.dao.login;

import com.mall.dao.base.IBaseDao;
import com.mall.entity.login.User;

public interface UserMapper  extends IBaseDao<User>{
	/**
	 * 登录
	 * @param user
	 * @return
	 */
	public User Login(User user);
	
   
}