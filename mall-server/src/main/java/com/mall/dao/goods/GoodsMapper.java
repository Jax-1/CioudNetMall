package com.mall.dao.goods;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mall.dao.base.IBaseDao;
import com.mall.entity.goods.Goods;

public interface GoodsMapper extends IBaseDao<Goods>{
	/**
	 * 批量删除
	 * @param list
	 * @return
	 */
	public int batchDelete(@Param("list")List<Goods> list) ;
	/**
	 * 批量上架
	 * @param list
	 * @return
	 */
	public int batchMarketableUp(@Param("list")List<Goods> list);
	/**
	 * 批量下架
	 * @param list
	 * @return
	 */
	public int batchMarketableDown(@Param("list")List<Goods> list);


}