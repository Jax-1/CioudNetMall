package com.mall.service.goods.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mall.dao.base.IBaseDao;
import com.mall.dao.goods.GoodsPriceMapper;
import com.mall.entity.goods.GoodsPrice;
import com.mall.service.BaseServiceImpl;
import com.mall.service.goods.GoodsPriceService;
import com.mall.util.PageResult;

@Service
public class GoodsPriceServiceImpl extends BaseServiceImpl<GoodsPrice> implements GoodsPriceService {
	@Resource
	private GoodsPriceMapper goodsPriceMapper;
	@Override
	protected IBaseDao<GoodsPrice> getMapper() {
		return goodsPriceMapper;
	}

	
}
