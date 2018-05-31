package com.mall.dao.cms;

import com.mall.entity.cms.AtticleldComment;

public interface AtticleldCommentMapper {
    int deleteByPrimaryKey(String commentId);

    int insert(AtticleldComment record);

    int insertSelective(AtticleldComment record);

    AtticleldComment selectByPrimaryKey(String commentId);

    int updateByPrimaryKeySelective(AtticleldComment record);

    int updateByPrimaryKeyWithBLOBs(AtticleldComment record);

    int updateByPrimaryKey(AtticleldComment record);
}