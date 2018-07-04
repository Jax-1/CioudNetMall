package com.mall.dao.goods;

import com.mall.dao.base.IBaseDao;
import com.mall.entity.goods.Goods;

public interface GoodsMapper extends IBaseDao<Goods>{
	/**
	 * 插入商品信息
	 * @param goods
	 * @return
	 */
	public int insertGoods(Goods goods);

}