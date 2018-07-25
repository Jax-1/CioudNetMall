package com.mall.service.order.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mall.dao.base.IBaseDao;
import com.mall.dao.order.OrderActionMapper;
import com.mall.entity.order.OrderAction;
import com.mall.service.BaseServiceImpl;
import com.mall.service.order.OrderActionService;
import com.mall.util.PageResult;

@Service
public class OrderActionServiceImpl extends BaseServiceImpl<OrderAction> implements OrderActionService {
	@Resource
	private OrderActionMapper orderActionMapper;
	@Override
	protected IBaseDao<OrderAction> getMapper() {
		return orderActionMapper;
	}

}
