package com.mall.service.Seckill;

import java.util.List;

import com.mall.dao.base.IBaseDao;
import com.mall.entity.seckill.Seckill;
import com.mall.execution.RepeatKillException;
import com.mall.execution.SeckillCloseException;
import com.mall.execution.SeckillException;
import com.mall.message.ProcessResult;

public interface SeckillService extends IBaseDao<Seckill>{
	
 
    /**
     * 查询单个秒杀记录
     * @param seckillId
     * @return
     */
    Seckill getById(long seckillId);
 
    /**
     * 在秒杀开启时输出秒杀接口的地址，否则输出系统时间和秒杀时间
     */
//    Exposer exportSeckillUrl(long seckillId);
 
    /**
     *  执行秒杀操作，有可能失败，有可能成功，所以要抛出我们允许的异常
     * @param seckillId
     * @param userPhone
     * @param md5
     * @return
     * @throws SeckillException
     * @throws RepeatKillException
     * @throws SeckillCloseException
     */
    ProcessResult<Seckill> executeSeckill(long seckillId, long userPhone, String md5)
            throws SeckillException, RepeatKillException, SeckillCloseException;
 
    ProcessResult<Seckill> executeSeckillProcedure(long seckillId,long userPhone,String md5)
            throws SeckillException,RepeatKillException,SeckillCloseException;

}
