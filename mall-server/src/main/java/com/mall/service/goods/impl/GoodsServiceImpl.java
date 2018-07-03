package com.mall.service.goods.impl;

import org.springframework.stereotype.Service;

import com.mall.dao.base.IBaseDao;
import com.mall.entity.goods.Goods;
import com.mall.service.BaseServiceImpl;
import com.mall.service.goods.GoodsService;

@Service
public class GoodsServiceImpl extends BaseServiceImpl<Goods> implements GoodsService{

	@Override
	protected IBaseDao<Goods> getMapper() {
		// TODO Auto-generated method stub
		return null;
	}

}
