package com.mall.service.cms.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mall.dao.base.IBaseDao;
import com.mall.dao.cms.AtticleldMapper;
import com.mall.entity.cms.Atticleld;
import com.mall.message.ProcessResult;
import com.mall.message.SystemCode;
import com.mall.service.BaseServiceImpl;
import com.mall.service.cms.AtticleldService;
import com.mall.service.sys.CacheService;


@Service
public class AtticleldServiceImpl extends BaseServiceImpl<Atticleld>implements AtticleldService {
	protected  Logger logger = Logger.getLogger(this.getClass());
	@Resource
	private  AtticleldMapper atticleldMapper;
	@Resource
	private CacheService cacheService;
	@Override
	protected IBaseDao<Atticleld> getMapper() {
		return atticleldMapper;
	}
	@Override
	public List<Atticleld> queryHotAtt(Atticleld att) {
		int pageSize  =  Integer.parseInt(cacheService.getCache(SystemCode.PAGE).get(SystemCode.MALL_ATT_REC_PAGE));
		List<Atticleld> hotAtt = atticleldMapper.queryHotAtt(att, pageSize);
		return hotAtt;
	}
	@Override
	public int updateLikeAndViewCount(Atticleld att) {
		
		return atticleldMapper.updateLikeAndViewCount(att);
	}
	
}


