package com.mall.dao.sys;

import java.util.List;

import com.mall.entity.sys.CodeItem;

public interface CodeItemMapper {
    int insert(CodeItem record);

    int insertSelective(CodeItem record);
    
    List<CodeItem> selectByCodeType(String TYPE_CODE);
    
}