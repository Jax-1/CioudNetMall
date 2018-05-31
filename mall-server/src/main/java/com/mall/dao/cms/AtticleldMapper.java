package com.mall.dao.cms;

import com.mall.entity.cms.Atticleld;

public interface AtticleldMapper {
    int deleteByPrimaryKey(String articleId);

    int insert(Atticleld record);

    int insertSelective(Atticleld record);

    Atticleld selectByPrimaryKey(String articleId);

    int updateByPrimaryKeySelective(Atticleld record);

    int updateByPrimaryKeyWithBLOBs(Atticleld record);

    int updateByPrimaryKey(Atticleld record);
}