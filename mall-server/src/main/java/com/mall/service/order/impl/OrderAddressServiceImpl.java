package com.mall.service.order.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mall.dao.base.IBaseDao;
import com.mall.dao.order.OrderAddressMapper;
import com.mall.entity.login.User;
import com.mall.entity.order.OrderAddress;
import com.mall.service.BaseServiceImpl;
import com.mall.service.order.OrderAddressService;
import com.mall.util.PageResult;

@Service
public class OrderAddressServiceImpl extends BaseServiceImpl<OrderAddress> implements OrderAddressService {
	@Resource
	private OrderAddressMapper orderAddressMapper;
	@Override
	protected IBaseDao<OrderAddress> getMapper() {
		return orderAddressMapper;
	}
	@Override
	public List<OrderAddress> userTakeDeliveryAddress(User user) {
		
		return orderAddressMapper.userTakeDeliveryAddress(user);
	}

	
}
