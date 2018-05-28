package com.mall.dao.login;

import com.mall.entity.login.User;

public interface UserMapper {
    int deleteByPrimaryKey(String user_name);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String user_name);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}