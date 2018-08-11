package com.mall.service.order.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mall.dao.base.IBaseDao;
import com.mall.dao.order.OrderServeMapper;
import com.mall.entity.order.OrderServe;
import com.mall.service.BaseServiceImpl;
import com.mall.service.order.OrderServeService;

@Service
public class OrderServeServiceImpl extends BaseServiceImpl<OrderServe> implements OrderServeService {
	@Resource
	private OrderServeMapper orderServeMapper;
	@Override
	protected IBaseDao<OrderServe> getMapper() {
		return orderServeMapper;
	}

}
