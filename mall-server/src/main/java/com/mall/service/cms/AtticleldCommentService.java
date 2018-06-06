package com.mall.service.cms;

import java.util.Map;

import com.mall.entity.cms.AtticleldComment;
import com.mall.message.ProcessResult;

/**
 * 文章评论服务类
 * @author jax
 *
 */
public interface AtticleldCommentService {
	/**
	 * 查询文章评论
	 * @param attcon
	 * @return
	 */
	public Map queryAtticleldComment(String attId);
	/**
	 * 添加文章评论
	 * @param attcon
	 * @return
	 */
	public ProcessResult<AtticleldComment> insert(AtticleldComment attcon);
	
}
