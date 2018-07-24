package com.mall.service.inventory;

import com.mall.entity.inventory.Inventory;
import com.mall.service.IBaseService;

public interface InventoryService extends IBaseService<Inventory>{
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
