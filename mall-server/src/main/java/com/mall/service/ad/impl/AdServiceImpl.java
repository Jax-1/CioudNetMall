package com.mall.service.ad.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mall.dao.ad.AdMapper;
import com.mall.dao.base.IBaseDao;
import com.mall.entity.ad.Ad;
import com.mall.service.BaseServiceImpl;
import com.mall.service.ad.AdService;
import com.mall.util.PageResult;

@Service
public class AdServiceImpl extends BaseServiceImpl<Ad> implements AdService {
	@Resource
	private AdMapper adMapper;
	@Override
	protected IBaseDao<Ad> getMapper() {
		return adMapper;
	}
	@Override
	public int updateAdStatus(Ad ad) {
		return adMapper.updateAdStatus(ad);
	}

	
}
