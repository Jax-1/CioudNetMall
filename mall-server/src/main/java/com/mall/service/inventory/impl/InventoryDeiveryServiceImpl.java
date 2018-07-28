package com.mall.service.inventory.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mall.dao.base.IBaseDao;
import com.mall.dao.inventory.InventoryDeiveryMapper;
import com.mall.entity.inventory.InventoryDeivery;
import com.mall.service.BaseServiceImpl;
import com.mall.service.inventory.InventoryDeiveryService;
import com.mall.util.PageResult;

@Service
public class InventoryDeiveryServiceImpl extends BaseServiceImpl<InventoryDeivery> implements InventoryDeiveryService {
	@Resource
	private InventoryDeiveryMapper inventoryDeiveryMapper;
	@Override
	protected IBaseDao<InventoryDeivery> getMapper() {
		return inventoryDeiveryMapper;
	}
	@Override
	public int updateDeiveryStatus(InventoryDeivery inventoryDeivery) {
		return inventoryDeiveryMapper.updateDeiveryStatus(inventoryDeivery);
	}
	@Override
	public InventoryDeivery selectByNumber(InventoryDeivery inventoryDeivery) {
		return inventoryDeiveryMapper.selectByNumber(inventoryDeivery);
	}
	


}
