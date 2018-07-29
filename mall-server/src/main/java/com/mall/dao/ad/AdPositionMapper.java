package com.mall.dao.ad;

import java.util.List;

import com.mall.dao.base.IBaseDao;
import com.mall.entity.ad.AdPosition;

public interface AdPositionMapper extends IBaseDao<AdPosition>{
	
	/**
	 * 获取全部广告位
	 * @return
	 */
	List<AdPosition> getAdPositionList(AdPosition adPosition);

}