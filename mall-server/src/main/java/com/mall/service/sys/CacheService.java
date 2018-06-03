package com.mall.service.sys;

import java.util.Map;

import com.mall.entity.sys.CodeItem;

public interface CacheService {
	/**
	 * 生成字段表数据缓存
	 * @return
	 */
	public CodeItem saveCache();
	/**
	 * 获取缓存
	 * @param codeType
	 * @return
	 */
	public Map<String,String> getCache(String codeType);

}
