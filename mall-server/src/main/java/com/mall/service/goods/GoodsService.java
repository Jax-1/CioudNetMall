package com.mall.service.goods;

import java.util.List;

import com.mall.entity.goods.Goods;
import com.mall.service.IBaseService;

public interface GoodsService extends IBaseService<Goods>{
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
