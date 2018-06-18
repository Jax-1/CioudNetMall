package com.mall.service.cms.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mall.dao.base.IBaseDao;
import com.mall.dao.cms.AtticleldMapper;
import com.mall.entity.cms.Atticleld;
import com.mall.message.ProcessResult;
import com.mall.message.SystemCode;
import com.mall.service.BaseServiceImpl;
import com.mall.service.cms.AtticleldService;
import com.mall.service.sys.CacheService;

import io.swagger.models.auth.In;

@Service
public class AtticleldServiceImpl extends BaseServiceImpl<Atticleld>implements AtticleldService {
	protected  Logger logger = Logger.getLogger(this.getClass());
	@Resource
	private  AtticleldMapper atticleldMapper;
	@Override
	protected IBaseDao<Atticleld> getMapper() {
		return atticleldMapper;
	}
	
//	@Override
//	public boolean save(Atticleld att) {
//		int insert = atticleldMapper.insert(att);
//		if(insert>0) {
//			return true;
//		}
//		return false;
//	}
//
//
//	@Override
//	public Page<Atticleld> queryList( String parentId ,int pageNow,int pageSize) {
//		
//		Page<Atticleld> page = PageHelper.startPage(pageNow,pageSize);
//		
//		List<Atticleld> list = atticleldMapper.findList( parentId);
//		for(Atticleld att:list) {
//			System.out.println(att.getCreateat());
//		}
//		logger.info("分类"+parentId+"总共有:"+page.getTotal()+"条数据,实际返回:"+list.size()+"条数据!");
//		return page;
//	}
//
//
//	@Override
//	public ProcessResult<Atticleld> delete(String articleId) {
//		ProcessResult<Atticleld> res=new ProcessResult<Atticleld>();
//		int dalete = atticleldMapper.dalete(articleId);
//		if(dalete>0) {
//			res.setRes(SystemCode.SUCCESS);
//			return res;
//		}
//		res.setMsg("删除文章失败！");
//		return res;
//	}
//
//
//	@Override
//	public Atticleld queryInfo(String articleId) {
//		
//		return atticleldMapper.selectInfo(articleId);
//	}
}


