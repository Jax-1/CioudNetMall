package com.mall.service.inventory.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mall.dao.base.IBaseDao;
import com.mall.dao.inventory.InventoryMapper;
import com.mall.entity.inventory.Inventory;
import com.mall.service.BaseServiceImpl;
import com.mall.service.inventory.InventoryService;
import com.mall.util.PageResult;

@Service
public class InventoryServiceImpl extends BaseServiceImpl<Inventory> implements InventoryService {
	@Resource
	private InventoryMapper inventoryMapper;
	@Override
	protected IBaseDao<Inventory> getMapper() {
		return inventoryMapper;
	}
	@Override
	public Inventory inventoryRetrieve(Inventory Inventory) {
		return inventoryMapper.inventoryRetrieve(Inventory);
	}

	
}
