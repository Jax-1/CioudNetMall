package com.mall.service.cms;

import java.util.List;

import com.mall.entity.cms.Atticleld;
import com.mall.entity.cms.Page;

/**
 * 文章服务类
 * @author jax
 *
 */
public interface AtticleldService {
	/**
	 * 保存文章信息
	 * @return
	 */
	public boolean save(Atticleld att) ;
	/**
	 * 获取文章列表
	 * @return
	 */
	public List<Atticleld> queryList(Page group);
}
