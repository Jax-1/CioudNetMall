package com.mall.dao.goods;

import java.util.List;

import com.mall.dao.base.IBaseDao;
import com.mall.entity.goods.GoodsCategory;

public interface GoodsCategoryMapper extends IBaseDao<GoodsCategory>{
	/**
	 * 获取分类信息
	 * @param goodsCategory
	 * @return
	 */
	public List<GoodsCategory> getGoodsCategoryList(GoodsCategory goodsCategory);
	/**
	 * 获取分类信息
	 * 子分类，一层
	 * @param goodsCategory
	 * @return
	 */
	public List<GoodsCategory> getGoodsCategoryOneList(GoodsCategory goodsCategory);
    
}