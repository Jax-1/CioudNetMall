package com.mall.service.cms.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mall.dao.cms.AtticleldMapper;
import com.mall.entity.cms.Atticleld;
import com.mall.entity.cms.Page;
import com.mall.service.cms.AtticleldService;

@Service
public class AtticleldServiceImpl implements AtticleldService {
	@Resource
	private  AtticleldMapper atticleldMapper;
	@Override
	public boolean save(Atticleld att) {
		int insert = atticleldMapper.insert(att);
		if(insert>0) {
			return true;
		}
		return false;
	}

	@Override
	public List<Atticleld> queryList(Page group) {
		
		return null;
	}

}
