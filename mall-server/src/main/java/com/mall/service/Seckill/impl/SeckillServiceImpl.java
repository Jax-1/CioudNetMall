package com.mall.service.Seckill.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.apache.commons.collections4.MapUtils;

import com.mall.dao.base.IBaseDao;
import com.mall.dao.seckill.SeckillMapper;
import com.mall.dao.seckill.SeckillSuccessMapper;
import com.mall.entity.seckill.Seckill;
import com.mall.entity.seckill.SeckillSuccess;
import com.mall.enums.SeckillStatEnum;
import com.mall.execution.RepeatKillException;
import com.mall.execution.SeckillCloseException;
import com.mall.execution.SeckillException;
import com.mall.message.ProcessResult;
import com.mall.service.BaseServiceImpl;
import com.mall.service.Seckill.SeckillService;
import com.mall.util.PageResult;

@Service
public class SeckillServiceImpl extends BaseServiceImpl<Seckill> implements SeckillService {
	 @Autowired
	 private SeckillMapper seckillDao;
	 @Autowired
	 private SeckillSuccessMapper successKilledDao;
	//加入一个混淆字符串(秒杀接口)的salt，为了我避免用户猜出我们的md5值，值任意给，越复杂越好
	 private final String salt = "sadjgioqwelrhaljflutoiu293480523*&%*&*#";

	
	@Override
	protected IBaseDao<Seckill> getMapper() {
		return seckillDao;
	}


	@Override
	public Seckill getById(long seckillId) {
		return seckillDao.selectByPrimaryKey(Long.toString(seckillId));
	}

	@Override

    @Transactional
	public ProcessResult<Seckill> executeSeckill(long seckillId, long userPhone, String md5)
			throws SeckillException, RepeatKillException, SeckillCloseException {
		ProcessResult<Seckill> res =new ProcessResult<Seckill>();
		 //执行秒杀逻辑：减库存+记录购买行为
        Date nowTime = new Date();
        try {
            //否则更新了库存，秒杀成功,增加明细
            int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
            //看是否该明细被重复插入，即用户是否重复秒杀
            if (insertCount <= 0) {
                throw new RepeatKillException("seckill repeated");
            } else {
 
                //减库存,热点商品竞争，update方法会拿到行级锁
                int updateCount = seckillDao.reduceNumber(seckillId, nowTime);
                if (updateCount <= 0) {
                    //没有更新库存记录，说明秒杀结束 rollback
                    throw new SeckillCloseException("seckill is closed");
                } else {
                    //秒杀成功,得到成功插入的明细记录,并返回成功秒杀的信息 commit
                	SeckillSuccess successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
                	res=ProcessResult.success(res);
                	res.setMsg(SeckillStatEnum.SUCCESS.toString());
                    return res;
                }
 
            }
        } catch (SeckillCloseException e1) {
            throw e1;
        } catch (RepeatKillException e2) {
            throw e2;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            //所有编译器异常，转化成运行期异常
            throw new SeckillException("seckill inner error:" + e.getMessage());
        }
	}


	@Override
	public ProcessResult<Seckill> executeSeckillProcedure(long seckillId, long userPhone, String md5)
			throws SeckillException, RepeatKillException, SeckillCloseException {
			ProcessResult<Seckill> res =new ProcessResult<Seckill>();
			Date time = new Date();
	        Map<String, Object> map = new HashMap<String, Object>();
	        map.put("seckillId", seckillId);
	        map.put("phone", userPhone);
	        map.put("killTime", time);
	        map.put("result", null);
	        try {
	            seckillDao.killByProcedure(map);
	            int result = MapUtils.getInteger(map, "result", -2);
	            if (result == 1) {
	            	SeckillSuccess successKill = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
	            	res=ProcessResult.success(res);
                	res.setMsg(SeckillStatEnum.SUCCESS.toString());
	                return res;
	            } else {
	            	res.setMsg(SeckillStatEnum.stateOf(result).toString());
	                return res;
	            }
	        } catch (Exception e) {
	            logger.error(e.getMessage(), e);
	            res.setMsg(SeckillStatEnum.INNER_ERROR.toString());
	            return res;
	        }
	}



	
	
}
