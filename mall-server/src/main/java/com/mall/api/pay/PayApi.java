package com.mall.api.pay;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.api.BaseAPI;
import com.mall.entity.payment.PaymentMethod;
import com.mall.service.payment.PaymentMethodService;

@Controller
@RequestMapping("api/pay")
public class PayApi extends BaseAPI {
	@Resource
	private PaymentMethodService paymentMethodService;
	
	/**
	 * 获取支付方式
	 * @return
	 */
	@GetMapping("payment")
	@ResponseBody
	public List<PaymentMethod> getPayMent() {
		//获取支付方式
		return paymentMethodService.getPaymentMethod();
		
	}

}
