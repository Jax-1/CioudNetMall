package com.mall.service.payment;

import java.util.List;

import com.mall.entity.payment.PaymentMethod;
import com.mall.service.IBaseService;

public interface PaymentMethodService extends IBaseService<PaymentMethod> {
	/**
	 * 获取支付方式
	 * @return
	 */
	public List<PaymentMethod> getPaymentMethod();

}
