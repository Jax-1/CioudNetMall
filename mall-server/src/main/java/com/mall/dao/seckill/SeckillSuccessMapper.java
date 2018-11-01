package com.mall.dao.seckill;

import com.mall.entity.seckill.SeckillSuccess;
import com.mall.entity.seckill.SeckillSuccessKey;

public interface SeckillSuccessMapper {
    int deleteByPrimaryKey(SeckillSuccessKey key);

    int insert(SeckillSuccess record);

    int insertSelective(SeckillSuccess record);

    SeckillSuccess selectByPrimaryKey(SeckillSuccessKey key);

    int updateByPrimaryKeySelective(SeckillSuccess record);

    int updateByPrimaryKey(SeckillSuccess record);
}