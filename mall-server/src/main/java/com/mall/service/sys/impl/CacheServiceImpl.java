package com.mall.service.sys.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.mall.dao.sys.CodeItemMapper;
import com.mall.entity.sys.CodeItem;
import com.mall.service.sys.CacheService;

@Service
public class CacheServiceImpl implements CacheService {
	protected  Logger logger = Logger.getLogger(this.getClass());
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
	@Cacheable(value = "codeItem", key = "#codeType")//3
	public Map<String, String> getCache(String codeType) {
		List<CodeItem> list = codeItemMapper.selectByCodeType(codeType);
		Map<String,String > map=new HashMap<String,String>();
		for(CodeItem code:list) {
			map.put(code.getITEM_CODE(), code.getITEM_NAME());
		}
		logger.info("初始化字典表缓存！缓存type："+codeType);
		logger.info(map);
		return map;
	}
	@Override
	@Cacheable(value = "goodsNum#1800", key = "#goodsId")
	public Map<String, Integer> toBePaidGoods(String goodsId, Integer num) {
		logger.info("初始化商品支付缓存！"+goodsId);
		Map<String,Integer > map=new HashMap<String,Integer>();
		map.put(goodsId, num);
		return map;
	}

	

}
