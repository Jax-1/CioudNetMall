package com.mall.service.cms.impl;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mall.dao.base.IBaseDao;
import com.mall.dao.cms.AuthorMapper;
import com.mall.entity.cms.AuthorWithBLOBs;
import com.mall.message.SystemCode;
import com.mall.service.BaseServiceImpl;
import com.mall.service.cms.AuthorWithBLOBsService;
import com.mall.service.sys.CacheService;

@Service
public class AuthorWithBLOBsServiceImpl extends BaseServiceImpl<AuthorWithBLOBs> implements AuthorWithBLOBsService {
	@Resource
	private AuthorMapper authorMapper;
	@Resource
	private CacheService cacheService;
	@Override
	protected IBaseDao<AuthorWithBLOBs> getMapper() {
		return authorMapper;
	}
	@Override
	public List<AuthorWithBLOBs> queryRecommendAtt(AuthorWithBLOBs auth) {
		int pageSize  =  Integer.parseInt(cacheService.getCache(SystemCode.PAGE).get(SystemCode.MALL_ATT_REC_PAGE));
		
		return authorMapper.queryRecommendAtt(auth, pageSize);
	}
	@Override
	public int updateLikeAndViewCount(AuthorWithBLOBs auth) {
		
		return authorMapper.updateLikeAndViewCount(auth);
	}

	
}
