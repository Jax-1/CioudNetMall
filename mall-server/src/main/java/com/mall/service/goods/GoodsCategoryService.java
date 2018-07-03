package com.mall.service.goods;

import java.util.List;

import com.mall.entity.goods.GoodsCategory;
import com.mall.service.IBaseService;

public interface GoodsCategoryService extends IBaseService<GoodsCategory>{
	/**
	 * 获取分类信息
	 * @param goodsCategory
	 * @return
	 */
	public List<GoodsCategory> getGoodsCategoryList(GoodsCategory goodsCategory);

}
