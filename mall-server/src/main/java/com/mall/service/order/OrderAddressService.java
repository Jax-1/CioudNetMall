package com.mall.service.order;

import java.util.List;

import com.mall.dao.base.IBaseDao;
import com.mall.entity.order.OrderAddress;
import com.mall.entity.user.User;

public interface OrderAddressService extends IBaseDao<OrderAddress>{
	/**
	 * 获取用户的收获地址
	 */
	public List<OrderAddress> userTakeDeliveryAddress( User user);
	/**
	 * 修改地址默认项
	 * @param orderAddress
	 * @return
	 */
	public boolean changeAddressDefault(OrderAddress orderAddress ,User user);
}
