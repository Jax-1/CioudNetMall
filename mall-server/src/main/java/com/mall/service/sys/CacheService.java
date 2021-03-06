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
	
	
	/**
	 * 将订单商品数目写入缓存，用于商品支付时校验库存
	 * 支付有效时间为10分钟
	 * 10分钟内支付完成，手动清除该缓存
	 * 10分钟未支付，自动清除该缓存
	 * @param goodsId
	 * @param num
	 * @return
	 */
	public Map<String,Integer> toBePaidGoods(String goodsId ,Integer num);
	
	/**
	 * 写待支付商品缓存
	 * @param goodsId
	 * @return
	 */
	public Map<String,Integer> writePaidGoods(String goodsId,Integer num);
	
	/**
	 * 清除正支付商品信息
	 * @param goodsId
	 */
	public void clearPaidGoods(String goodsId);
	
	
}
