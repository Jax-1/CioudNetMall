package com.mall.service.cms.impl;

import java.util.List;

import javax.annotation.Resource;

import com.mall.dao.cms.AtticleldCategoryMapper;
import com.mall.entity.cms.AtticleldCategory;
import com.mall.message.MessageUtil;
import com.mall.message.MsgPoolCode;
import com.mall.message.ProcessResult;
import com.mall.service.cms.AtticleldCategoryService;
import com.mall.util.Validate;

public class AtticleldCategoryServiceImpl implements AtticleldCategoryService {
	@Resource
	private AtticleldCategoryMapper atticleldCategoryMapper;
	@Override
	public ProcessResult<AtticleldCategory> insert(AtticleldCategory category) {
		ProcessResult<AtticleldCategory> result = new ProcessResult<AtticleldCategory>();
		result.setMsg(MessageUtil.getMsgByLan(MsgPoolCode.CATEGORY_INFO_ADD_FAILED));
		if(Validate.notNull(category)) {
			return result;
		}
		int insert = atticleldCategoryMapper.insert(category);
		if(insert>0) {
			result.setMsg("");
		}
		return result;
	}

	@Override
	public List<AtticleldCategory> queryAll() {
		
		return atticleldCategoryMapper.queryAll();
	}

}
