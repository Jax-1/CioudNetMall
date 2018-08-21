package com.mall.service.payment.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mall.dao.base.IBaseDao;
import com.mall.dao.payment.PaymentRefundMapper;
import com.mall.entity.payment.PaymentRefund;
import com.mall.service.BaseServiceImpl;
import com.mall.service.payment.PaymentRefundService;

@Service
public class PaymentRefundServiceImpl extends BaseServiceImpl<PaymentRefund> implements PaymentRefundService{
	@Resource
	private PaymentRefundMapper paymentRefundMapper;
	@Override
	protected IBaseDao<PaymentRefund> getMapper() {
		return paymentRefundMapper;
	}

}
