package com.mall.service.order.impl;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mall.dao.base.IBaseDao;
import com.mall.dao.order.OrderAddressMapper;
import com.mall.entity.order.OrderAddress;
import com.mall.entity.user.User;
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
	@Override
	public boolean changeAddressDefault(OrderAddress orderAddress,User user) {
		boolean flg=true;
		//获取用户所有收货地址
		List<OrderAddress> userTakeDeliveryAddress = orderAddressMapper.userTakeDeliveryAddress(user);
		for(OrderAddress orderAddressItem:userTakeDeliveryAddress) {
			orderAddressItem.setThedefault("N");
			if(orderAddressItem.getId().equals(orderAddress.getId())) {
				//设为默认
				orderAddressItem.setThedefault("Y");
			}
			int changeAddressDefault=0;
			try {
				changeAddressDefault = orderAddressMapper.updateByPrimaryKeySelective(orderAddressItem);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(changeAddressDefault<=0) {
				//修改失败
				flg=false;
			}
		}
		return flg;
	}

	
}
