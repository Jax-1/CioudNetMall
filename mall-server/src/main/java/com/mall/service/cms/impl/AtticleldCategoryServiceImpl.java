package com.mall.service.cms.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mall.dao.cms.AtticleldCategoryMapper;
import com.mall.entity.cms.AtticleldCategory;
import com.mall.message.MessageUtil;
import com.mall.message.MsgPoolCode;
import com.mall.message.ProcessResult;
import com.mall.message.SystemCode;
import com.mall.service.cms.AtticleldCategoryService;
import com.mall.util.MuneUtile;
import com.mall.util.Validate;

@Service
public class AtticleldCategoryServiceImpl implements AtticleldCategoryService {
	@Resource
	private AtticleldCategoryMapper atticleldCategoryMapper;
	@Override
	public ProcessResult<AtticleldCategory> insert(AtticleldCategory category) {
		ProcessResult<AtticleldCategory> result = new ProcessResult<AtticleldCategory>();
		result.setMsg(MessageUtil.getMsgByLan(MsgPoolCode.CATEGORY_INFO_ADD_FAILED));
		if(!Validate.notNull(category)) {
			return result;
		}
		int insert = atticleldCategoryMapper.insert(category);
		if(insert>0) {
			result.setRes(SystemCode.SUCCESS);
			result.setMsg("分类信息添加成功");
		}
		return result;
	}

	@Override
	public Map queryAll(AtticleldCategory att) {
		
		List<AtticleldCategory> list= atticleldCategoryMapper.queryAll();
		
		return MuneUtile.mune(list, att);
		 
	}
	
	
}