package com.mall.dao.seckill;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.mall.dao.base.IBaseDao;
import com.mall.entity.seckill.Seckill;

public interface SeckillMapper extends IBaseDao<Seckill>{
	
	 /**
     * 减库存
     * @param seckillId
     * @param killTime
     * @return 如果影响行数>1，表示更新库存的记录行数
     */
    int reduceNumber(@Param("seckillId") long seckillId, @Param("killTime") Date killTime);
    /**
     *批量删除
     * @param paramMap
     */
    int batchDelete(@Param("list")List<Seckill> list);
    
    
    void killByProcedure(Map<String,Object> paramMap);

    /**
     * 查询单个秒杀记录
     * @param seckillId
     * @return
     */
    Seckill getById(long seckillId);


}