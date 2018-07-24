package com.mall.dao.inventory;

import com.mall.dao.base.IBaseDao;
import com.mall.entity.inventory.Inventory;

public interface InventoryMapper extends IBaseDao<Inventory>{
	/**
	 * 检索商品库存
	 * @param Inventory
	 * @return
	 */
	public  Inventory inventoryRetrieve(Inventory Inventory);
	/**
	 * 更新库存信息
	 * @param Inventory
	 * @return
	 */
	public int updateInventory(Inventory Inventory);
}