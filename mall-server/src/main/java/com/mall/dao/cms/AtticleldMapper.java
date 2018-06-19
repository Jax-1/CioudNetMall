package com.mall.dao.cms;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mall.dao.base.IBaseDao;
import com.mall.entity.cms.Atticleld;

public interface AtticleldMapper extends IBaseDao<Atticleld>{
	/**
	 * 热门文章
	 * @return
	 */
	public List<Atticleld> queryHotAtt(@Param("att")Atticleld att,@Param("pageSize")int pageSize);
	/**
	 * 更新点赞数或查看次数
	 * @param att
	 * @return
	 */
	public int updateLikeAndViewCount(Atticleld att);
	
	
     
}