package com.mall.service.payment.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mall.dao.base.IBaseDao;
import com.mall.dao.payment.PaymentMapper;
import com.mall.entity.payment.Payment;
import com.mall.service.BaseServiceImpl;
import com.mall.service.payment.PaymentSrevice;

@Service
public class PaymentSreviceImpl extends BaseServiceImpl<Payment> implements PaymentSrevice {
	@Resource
	private PaymentMapper paymentMapper;
	
	@Override
	protected IBaseDao<Payment> getMapper() {
		// TODO Auto-generated method stub
		return paymentMapper;
	}

	
}
