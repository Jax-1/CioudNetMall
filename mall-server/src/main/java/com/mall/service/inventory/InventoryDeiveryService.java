package com.mall.service.inventory;

import com.mall.dao.base.IBaseDao;
import com.mall.entity.inventory.InventoryDeivery;

public interface InventoryDeiveryService extends IBaseDao<InventoryDeivery> {
	/**
	 * 修改发货单状态
	 * @param order
	 * @return
	 */
	public int updateDeiveryStatus(InventoryDeivery inventoryDeivery) ;
	/**
	 * 通过订单号或发货单号获取
	 * @param inventoryDeivery
	 * @return
	 */
	public InventoryDeivery selectByNumber(InventoryDeivery inventoryDeivery);

}
