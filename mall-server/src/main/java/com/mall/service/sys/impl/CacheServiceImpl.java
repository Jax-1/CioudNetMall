package com.mall.service.sys.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.mall.dao.sys.CodeItemMapper;
import com.mall.entity.sys.CodeItem;
import com.mall.service.sys.CacheService;

@Service
public class CacheServiceImpl implements CacheService {
	
	@Autowired
    private CodeItemMapper codeItemMapper;
	
	@Override
	public CodeItem saveCache() {
		List<CodeItem> codeItem = codeItemMapper.selectByCodeType("");
		
		return null;
	}
	/**
	 * 字典表缓存
	 */
	@Override
	//@Cacheable(value = "SystemCode", key = "#codeType")//3
	public Map<String, String> getCache(String codeType) {
		List<CodeItem> list = codeItemMapper.selectByCodeType(codeType);
		Map<String,String > map=new HashMap<String,String>();
		for(CodeItem code:list) {
			map.put(code.getITEM_CODE(), code.getITEM_NAME());
		}
		
		return map;
	}

	

}
