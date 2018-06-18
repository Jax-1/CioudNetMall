package com.mall.service.cms.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mall.dao.base.IBaseDao;
import com.mall.dao.cms.AuthorMapper;
import com.mall.entity.cms.AuthorWithBLOBs;
import com.mall.service.BaseServiceImpl;
import com.mall.service.cms.AuthorWithBLOBsService;

@Service
public class AuthorWithBLOBsServiceImpl extends BaseServiceImpl<AuthorWithBLOBs> implements AuthorWithBLOBsService {
	@Resource
	private AuthorMapper authorMapper;
	@Override
	protected IBaseDao<AuthorWithBLOBs> getMapper() {
		return authorMapper;
	}

	
}
