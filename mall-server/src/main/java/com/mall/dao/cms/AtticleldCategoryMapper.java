package com.mall.dao.cms;

import java.util.List;

import com.mall.entity.cms.AtticleldCategory;

public interface AtticleldCategoryMapper {
    int deleteByPrimaryKey(String id);

    int insert(AtticleldCategory record);

    int insertSelective(AtticleldCategory record);

    AtticleldCategory selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AtticleldCategory record);

    int updateByPrimaryKey(AtticleldCategory record);
    
    /**
     * 查询所有分类
     * @return
     */
    List<AtticleldCategory> queryAll(String parentId);
}