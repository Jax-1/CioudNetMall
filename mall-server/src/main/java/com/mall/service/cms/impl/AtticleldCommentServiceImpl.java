package com.mall.service.cms.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mall.dao.cms.AtticleldCommentMapper;
import com.mall.entity.cms.AtticleldComment;
import com.mall.message.ProcessResult;
import com.mall.service.cms.AtticleldCommentService;

@Service
public class AtticleldCommentServiceImpl implements AtticleldCommentService {
	@Resource
	private AtticleldCommentMapper atticleldCommentMapper;
	@Override
	public Map queryAtticleldComment(String attId) {
		
		return null;
	}

	@Override
	public ProcessResult<AtticleldComment> insert(AtticleldComment attcon) {
		// TODO Auto-generated method stub
		return null;
	}

}
