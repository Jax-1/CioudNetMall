package com.mall.service.cms;

import java.util.List;


import com.mall.entity.cms.Atticleld;
import com.mall.service.IBaseService;

/**
 * 文章服务类
 * @author jax
 *
 */
public interface AtticleldService extends IBaseService<Atticleld>{
	/**
	 * 热门文章
	 * @return
	 */
	public List<Atticleld> queryHotAtt(Atticleld att);
	/**
	 * 更新点赞数或查看次数
	 * @param att
	 * @return
	 */
	public int updateLikeAndViewCount(Atticleld att);
	
}
