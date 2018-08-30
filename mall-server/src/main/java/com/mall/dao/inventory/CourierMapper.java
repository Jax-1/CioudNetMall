package com.mall.dao.inventory;

import java.util.List;

import com.mall.dao.base.IBaseDao;
import com.mall.entity.inventory.Courier;

public interface CourierMapper extends IBaseDao<Courier>{
	
	/**
	 * 查询全部快递
	 * @param courier
	 * @return
	 */
	public List<Courier> queryAll(Courier courier);
   
}