package com.mall.dao.cms;

import com.mall.dao.base.IBaseDao;
import com.mall.entity.cms.AuthorWithBLOBs;
import com.mall.entity.cms.Author;

public interface AuthorMapper extends IBaseDao<AuthorWithBLOBs>{

	int deleteByPrimaryKey(String id);

	int insert(AuthorWithBLOBs record);

	int insertSelective(AuthorWithBLOBs record);

	AuthorWithBLOBs selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(AuthorWithBLOBs record);

	int updateByPrimaryKeyWithBLOBs(AuthorWithBLOBs record);

	int updateByPrimaryKey(Author record);

}