package com.mall.dao.goods;

import java.util.List;

import com.mall.dao.base.IBaseDao;
import com.mall.entity.goods.Goods;

public interface GoodsMapper extends IBaseDao<Goods>{
	/**
	 * 批量删除
	 * @param list
	 * @return
	 */
	public int batchDelete(List<Goods> list) ;
	/**
	 * 批量上架
	 * @param list
	 * @return
	 */
	public int batchMarketableUp(List<Goods> list);
	/**
	 * 批量下架
	 * @param list
	 * @return
	 */
	public int batchMarketableDown(List<Goods> list);

}