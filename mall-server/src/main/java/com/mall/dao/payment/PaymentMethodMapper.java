package com.mall.dao.payment;

import java.util.List;

import com.mall.dao.base.IBaseDao;
import com.mall.entity.payment.PaymentMethod;
import com.mall.entity.payment.PaymentMethodKey;

public interface PaymentMethodMapper extends IBaseDao<PaymentMethod>{
	/**
	 * 获取支付方式
	 * @return
	 */
	public List<PaymentMethod> getPaymentMethod();
   
}