package com.mall.service.goods;

import com.mall.entity.goods.Goods;
import com.mall.service.IBaseService;

public interface GoodsService extends IBaseService<Goods>{
	/**
	 * 插入商品信息
	 * @param goods
	 * @return
	 */
	public int insertGoods(Goods goods);
}
