package com.mall.dao.inventory;

import java.util.List;

import com.mall.dao.base.IBaseDao;
import com.mall.entity.inventory.Courier;

public interface CourierMapper extends IBaseDao<Courier>{
	
	public List<Courier> queryAll(Courier courier);
   
}