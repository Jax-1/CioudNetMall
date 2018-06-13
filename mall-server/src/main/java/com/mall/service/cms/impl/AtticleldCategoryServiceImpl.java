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
			result.setMsg("分类信息添加成功!");
		}
		return result;
	}

	@Override
	public List<AtticleldCategory> queryAll(String pid) {
		
		List<AtticleldCategory> list= atticleldCategoryMapper.queryAll(pid);
		
		return list;
		 
	}

	@Override
	public ProcessResult<AtticleldCategory> update(AtticleldCategory category) {
		ProcessResult<AtticleldCategory> result = new ProcessResult<AtticleldCategory>();
		if(!Validate.notNull(category)) {
			return result;
		}
		int update = atticleldCategoryMapper.updateByPrimaryKeySelective(category);
		if(update>0) {
			result.setRes(SystemCode.SUCCESS);
			result.setMsg("分类信息修改成功!");
			return result;
		}
		result.setMsg("分类信息修改失败!");
		return result;
	}

	@Override
	public ProcessResult<AtticleldCategory> delete(String id) {
		atticleldCategoryMapper.deleteByPrimaryKey(id);
		return new ProcessResult<AtticleldCategory>(SystemCode.SUCCESS,"");
	}
	
	
}
