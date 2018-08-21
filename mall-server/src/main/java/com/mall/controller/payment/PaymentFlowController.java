package com.mall.controller.payment;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.controller.AbstractController;
import com.mall.entity.payment.PaymentFlow;
import com.mall.message.ProcessResult;
import com.mall.message.SystemCode;
import com.mall.service.payment.PaymentFlowService;

@Controller
@RequestMapping("/payment")
public class PaymentFlowController extends AbstractController{
	@Resource
	private PaymentFlowService paymentFlowService;
	
	
	@RequestMapping("/info")
	@ResponseBody
	public ProcessResult<PaymentFlow> queryPaymentFlowInfo(PaymentFlow paymentFlow){
		ProcessResult<PaymentFlow> res=new ProcessResult<PaymentFlow>();
		paymentFlow=paymentFlowService.selectByPrimaryKey(paymentFlow.getAttach());
		res.setRes(SystemCode.SUCCESS);
		res.setObj(paymentFlow);
		return res;
		
	}
}
