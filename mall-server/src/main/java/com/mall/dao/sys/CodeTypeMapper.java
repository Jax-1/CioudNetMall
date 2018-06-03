package com.mall.dao.sys;

import com.mall.entity.sys.CodeType;

public interface CodeTypeMapper {
    int deleteByPrimaryKey(String TYPE_CODE);

    int insert(CodeType record);

    int insertSelective(CodeType record);

    CodeType selectByPrimaryKey(String TYPE_CODE);

    int updateByPrimaryKeySelective(CodeType record);

    int updateByPrimaryKey(CodeType record);
}