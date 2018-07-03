package com.mall.service.goods.impl;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mall.dao.base.IBaseDao;
import com.mall.dao.goods.GoodsCategoryMapper;
import com.mall.entity.goods.GoodsCategory;
import com.mall.service.BaseServiceImpl;
import com.mall.service.goods.GoodsCategoryService;

@Service
public class GoodsCategoryServiceImpl extends BaseServiceImpl<GoodsCategory> implements GoodsCategoryService {
	@Resource
	private GoodsCategoryMapper goodsCategoryMapper;
	@Override
	protected IBaseDao<GoodsCategory> getMapper() {
		return goodsCategoryMapper;
	}

	@Override
	public List<GoodsCategory> getGoodsCategoryList(GoodsCategory goodsCategory) {
		List<GoodsCategory> goodsCategoryList = goodsCategoryMapper.getGoodsCategoryList(goodsCategory);
		for(GoodsCategory g:goodsCategoryList) {
			List<GoodsCategory> list = getGoodsCategoryList(g);
			g.setGoodsCategoryList(list);
		}
		return goodsCategoryList;
	}

	
}
