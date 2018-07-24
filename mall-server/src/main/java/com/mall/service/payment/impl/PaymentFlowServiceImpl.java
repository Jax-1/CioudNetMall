package com.mall.service.payment.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mall.dao.base.IBaseDao;
import com.mall.dao.payment.PaymentFlowMapper;
import com.mall.entity.payment.PaymentFlow;
import com.mall.service.BaseServiceImpl;
import com.mall.service.payment.PaymentFlowService;
import com.mall.util.PageResult;

@Service
public class PaymentFlowServiceImpl extends BaseServiceImpl<PaymentFlow> implements PaymentFlowService {
	@Resource
	private PaymentFlowMapper paymentFlowMapper;
	@Override
	protected IBaseDao<PaymentFlow> getMapper() {
		return paymentFlowMapper;
	}

	
}
