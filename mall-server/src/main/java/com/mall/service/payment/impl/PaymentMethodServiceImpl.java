package com.mall.service.payment.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mall.dao.base.IBaseDao;
import com.mall.dao.payment.PaymentMethodMapper;
import com.mall.entity.payment.PaymentMethod;
import com.mall.service.BaseServiceImpl;
import com.mall.service.payment.PaymentMethodService;
import com.mall.util.PageResult;

@Service
public class PaymentMethodServiceImpl extends BaseServiceImpl<PaymentMethod> implements PaymentMethodService {
	@Resource
	private PaymentMethodMapper paymentMethodMapper;
	@Override
	protected IBaseDao<PaymentMethod> getMapper() {
		return paymentMethodMapper;
	}
	@Override
	public List<PaymentMethod> getPaymentMethod() {
		return paymentMethodMapper.getPaymentMethod();
	}


}
