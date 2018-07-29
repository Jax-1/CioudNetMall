package com.mall.dao.ad;

import com.mall.dao.base.IBaseDao;
import com.mall.entity.ad.Ad;

public interface AdMapper extends IBaseDao<Ad>{
	
	/**
	 * 更改广告状态
	 * @param ad
	 * @return
	 */
	public int updateAdStatus(Ad ad);
}