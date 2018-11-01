package com.mall.dao.order;

import java.util.List;

import com.mall.dao.base.IBaseDao;
import com.mall.entity.order.OrderAddress;
import com.mall.entity.user.User;

public interface OrderAddressMapper extends IBaseDao<OrderAddress>{
	/**
	 * 获取用户的收获地址
	 */
	public List<OrderAddress> userTakeDeliveryAddress( User user);
	

}