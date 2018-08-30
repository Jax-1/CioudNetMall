package com.mall.service.inventory.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mall.dao.base.IBaseDao;
import com.mall.dao.inventory.CourierMapper;
import com.mall.entity.inventory.Courier;
import com.mall.service.BaseServiceImpl;
import com.mall.service.inventory.CourierService;
import com.mall.util.PageResult;

@Service
public class CourierServiceImpl extends BaseServiceImpl<Courier> implements CourierService {
	@Resource
	private CourierMapper courierMapper;
	@Override
	protected IBaseDao<Courier> getMapper() {
		return courierMapper;
	}

	
}
