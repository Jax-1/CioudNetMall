package com.mall.service.inventory.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mall.dao.base.IBaseDao;
import com.mall.dao.inventory.InventoryDeiveryActionMapper;
import com.mall.entity.inventory.InventoryDeiveryAction;
import com.mall.service.BaseServiceImpl;
import com.mall.service.inventory.InventoryDeiveryActionService;
import com.mall.util.PageResult;

@Service
public class InventoryDeiveryActionServiceImpl extends BaseServiceImpl<InventoryDeiveryAction>
		implements InventoryDeiveryActionService {
	@Resource
	private InventoryDeiveryActionMapper inventoryDeiveryActionMapper;
	@Override
	protected IBaseDao<InventoryDeiveryAction> getMapper() {
		return inventoryDeiveryActionMapper;
	}

}
