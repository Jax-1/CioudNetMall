package com.mall.dao.order;

import com.mall.dao.base.IBaseDao;
import com.mall.entity.order.Order;

public interface OrderMapper  extends IBaseDao<Order>{
	
	/**
	 * 修改订单状态
	 * @param order
	 * @return
	 */
	public int updateOrderStatus(Order order) ;
}