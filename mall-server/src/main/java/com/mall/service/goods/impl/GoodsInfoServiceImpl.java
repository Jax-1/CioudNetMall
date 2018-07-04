package com.mall.service.goods.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mall.dao.base.IBaseDao;
import com.mall.dao.goods.GoodsInfoMapper;
import com.mall.entity.goods.GoodsInfo;
import com.mall.service.BaseServiceImpl;
import com.mall.service.goods.GoodsInfoService;
import com.mall.util.PageResult;

@Service
public class GoodsInfoServiceImpl extends BaseServiceImpl<GoodsInfo> implements GoodsInfoService {
	@Resource
	private GoodsInfoMapper goodsInfoMapper;
	@Override
	protected IBaseDao<GoodsInfo> getMapper() {
		return goodsInfoMapper;
	}

	
}
