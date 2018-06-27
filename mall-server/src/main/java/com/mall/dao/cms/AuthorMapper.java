package com.mall.dao.cms;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mall.dao.base.IBaseDao;
import com.mall.entity.cms.AuthorWithBLOBs;

public interface AuthorMapper extends IBaseDao<AuthorWithBLOBs>{

	/**
	 * 推荐作家
	 * @return
	 */
	public List<AuthorWithBLOBs> queryRecommendAtt(@Param("auth")AuthorWithBLOBs auth,@Param("pageSize")int pageSize);
	/**
	 * 更新点赞数或查看次数
	 * @param att
	 * @return
	 */
	public int updateLikeAndViewCount(AuthorWithBLOBs auth);
	/**
	 * 查询作家地区分布
	 * @return
	 */
	public List<AuthorWithBLOBs> queryAddress();
	/**
	 * 查询作家职位分布
	 * @return
	 */
	public List<AuthorWithBLOBs> queryPosition();

}