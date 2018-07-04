package com.mall.dao.inventory;

import com.mall.entity.inventory.Inventory;

public interface InventoryMapper {
    int deleteByPrimaryKey(String inventoryid);

    int insert(Inventory record);

    int insertSelective(Inventory record);

    Inventory selectByPrimaryKey(String inventoryid);

    int updateByPrimaryKeySelective(Inventory record);

    int updateByPrimaryKey(Inventory record);
}