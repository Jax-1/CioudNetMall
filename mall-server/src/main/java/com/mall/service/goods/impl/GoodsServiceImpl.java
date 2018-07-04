package com.mall.service.goods.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mall.dao.base.IBaseDao;
import com.mall.dao.goods.GoodsMapper;
import com.mall.entity.goods.Goods;
import com.mall.service.BaseServiceImpl;
import com.mall.service.goods.GoodsService;

@Service
public class GoodsServiceImpl extends BaseServiceImpl<Goods> implements GoodsService{
	@Resource
	private GoodsMapper goodsMapper;
	@Override
	protected IBaseDao<Goods> getMapper() {
		return goodsMapper;
	}

	@Override
	public int insertGoods(Goods goods) {
		
		return goodsMapper.insertGoods(goods);
	}

}
