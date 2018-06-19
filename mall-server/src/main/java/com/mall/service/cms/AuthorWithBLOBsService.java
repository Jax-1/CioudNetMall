package com.mall.service.cms;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mall.entity.cms.AuthorWithBLOBs;
import com.mall.service.IBaseService;

public interface AuthorWithBLOBsService extends IBaseService<AuthorWithBLOBs>{
	/**
	 * 推荐作家
	 * @return
	 */
	public List<AuthorWithBLOBs> queryRecommendAtt(AuthorWithBLOBs auth);
	/**
	 * 更新点赞数或查看次数
	 * @param att
	 * @return
	 */
	public int updateLikeAndViewCount(AuthorWithBLOBs auth);

}
