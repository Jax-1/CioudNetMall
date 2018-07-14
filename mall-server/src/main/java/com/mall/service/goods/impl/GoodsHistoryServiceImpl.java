package com.mall.service.goods.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mall.dao.base.IBaseDao;
import com.mall.dao.goods.GoodsHistoryMapper;
import com.mall.entity.goods.GoodsHistory;
import com.mall.service.BaseServiceImpl;
import com.mall.service.goods.GoodsHistoryService;

@Service
public class GoodsHistoryServiceImpl extends BaseServiceImpl<GoodsHistory> implements GoodsHistoryService {
	@Resource
	private GoodsHistoryMapper goodsHistoryMapper;
	@Override
	protected IBaseDao<GoodsHistory> getMapper() {
		return goodsHistoryMapper;
	}

	
}
