package com.mall.service.cms;

import java.util.List;

import com.github.pagehelper.Page;
import com.mall.entity.cms.Atticleld;

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
	public Page<Atticleld> queryList(String parentId,int pageNow,int pageSize);
	
}
