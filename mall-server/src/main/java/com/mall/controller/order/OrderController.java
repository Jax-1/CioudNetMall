package com.mall.controller.order;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mall.controller.AbstractController;
import com.mall.entity.goods.Goods;
import com.mall.entity.login.User;
import com.mall.util.SessionUtil;

/**
 * 订单
 * @author Jang
 *
 */
@Controller
@RequestMapping("/mall/order")
public class OrderController extends AbstractController{
	
	@RequestMapping("")
	public String toOrder(Model model,Goods goods,HttpServletRequest request) {
		User user = SessionUtil.getUser(request);
		
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
