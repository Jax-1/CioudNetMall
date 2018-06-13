package com.mall.service.cms;

import java.util.List;
import java.util.Map;

import com.mall.entity.cms.AtticleldCategory;
import com.mall.message.ProcessResult;

public interface AtticleldCategoryService {
	/**
	 * 插入分类信息
	 * @param category
	 * @return
	 */
	public ProcessResult<AtticleldCategory> insert(AtticleldCategory category);
	/**
	 * 查询所有分类信息
	 * @return
	 */
	public List<AtticleldCategory> queryAll(String pid);
	
	/**
	 * 修改分类信息
	 * @param category
	 * @return
	 */
	public ProcessResult<AtticleldCategory> update(AtticleldCategory category);
	/**
	 * 删除分类
	 * @param id
	 * @return
	 */
	public ProcessResult<AtticleldCategory> delete(String id);
	
}
