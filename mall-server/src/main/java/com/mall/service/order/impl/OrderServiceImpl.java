package com.mall.service.order.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mall.dao.base.IBaseDao;
import com.mall.dao.order.OrderMapper;
import com.mall.entity.order.Order;
import com.mall.service.BaseServiceImpl;
import com.mall.service.order.OrderService;
import com.mall.util.PageResult;
@Service
public class OrderServiceImpl extends BaseServiceImpl<Order> implements OrderService {
	@Resource
	private OrderMapper orderMapper;
	@Override
	protected IBaseDao<Order> getMapper() {
		return orderMapper;
	}
	@Override
	public int updateOrderStatus(Order order) {
		return orderMapper.updateOrderStatus(order);
	}

	
}
