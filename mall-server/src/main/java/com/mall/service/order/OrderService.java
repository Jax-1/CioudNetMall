package com.mall.service.order;

import java.util.List;

import com.mall.dao.base.IBaseDao;
import com.mall.entity.goods.Goods;
import com.mall.entity.order.Order;

public interface OrderService extends IBaseDao<Order>{
	/**
	 * 修改订单状态
	 * @param order
	 * @return
	 */
	public int updateOrderStatus(Order order) ;
	
	/**
	 * 计算订单金额
	 * @param order
	 * @return
	 */
	public Order calculationOrderAmount(Order order);
}
