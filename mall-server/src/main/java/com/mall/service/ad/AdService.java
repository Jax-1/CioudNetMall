package com.mall.service.ad;

import com.mall.entity.ad.Ad;
import com.mall.service.IBaseService;

public interface AdService extends IBaseService<Ad> {
	/**
	 * 更改广告状态
	 * @param ad
	 * @return
	 */
	public int updateAdStatus(Ad ad);

}
