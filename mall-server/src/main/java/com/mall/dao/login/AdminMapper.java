package com.mall.dao.login;

import com.mall.entity.login.Admin;

public interface AdminMapper {
    int deleteByPrimaryKey(String admin_name);

    int insert(Admin record);

    int insertSelective(Admin record);

    Admin selectByPrimaryKey(String admin_name);

    int updateByPrimaryKeySelective(Admin record);

    int updateByPrimaryKey(Admin record);
}