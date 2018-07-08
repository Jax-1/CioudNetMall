package com.mall.controller.order;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mall.controller.AbstractController;

/**
 * 订单
 * @author Jang
 *
 */
@Controller
@RequestMapping("/mall/order")
public class OrderController extends AbstractController{
	
	@RequestMapping("")
	public String toOrder(Model model) {
		model.addAttribute("page", "mall/order/order");
		return "mall/index";
		
	}
	@RequestMapping("/pay")
	public String toOrderPay(Model model) {
		model.addAttribute("page", "mall/order/order_pay");
		return "mall/index";
		
	}
	@RequestMapping("/real")
	public String toOrderReal(Model model) {
		model.addAttribute("page", "mall/order/order_real");
		return "mall/index";
		
	}

}
