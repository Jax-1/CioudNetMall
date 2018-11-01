package com.mall.dao.seckill;

import com.mall.entity.seckill.Seckill;

public interface SeckillMapper {
    int deleteByPrimaryKey(Long seckill_id);

    int insert(Seckill record);

    int insertSelective(Seckill record);

    Seckill selectByPrimaryKey(Long seckill_id);

    int updateByPrimaryKeySelective(Seckill record);

    int updateByPrimaryKey(Seckill record);
}