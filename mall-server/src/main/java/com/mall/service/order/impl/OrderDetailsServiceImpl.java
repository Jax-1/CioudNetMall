package com.mall.service.order.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mall.dao.base.IBaseDao;
import com.mall.dao.order.OrderDetailsMapper;
import com.mall.entity.order.OrderDetails;
import com.mall.service.BaseServiceImpl;
import com.mall.service.order.OrderDetailsService;
import com.mall.util.PageResult;

@Service
public class OrderDetailsServiceImpl extends BaseServiceImpl<OrderDetails> implements OrderDetailsService {
	@Resource
	private OrderDetailsMapper orderDetailsMapper;
	@Override
	protected IBaseDao<OrderDetails> getMapper() {
		return orderDetailsMapper;
	}

	
}
