package com.mall.controller.order;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mall.controller.AbstractController;
import com.mall.entity.goods.Goods;
import com.mall.entity.login.User;
import com.mall.entity.order.OrderAddress;
import com.mall.service.goods.GoodsService;
import com.mall.service.order.OrderAddressService;
import com.mall.util.SessionUtil;

/**
 * 订单
 * @author Jang
 *
 */
@Controller
@RequestMapping("/mall/order")
public class OrderController extends AbstractController{
	@Resource
	private OrderAddressService orderAddressService;
	@Resource
	private GoodsService goodsService;
	@RequestMapping("")
	public String toOrder(Model model,Goods goods,HttpServletRequest request ,Integer amount) {
		User user = SessionUtil.getUser(request);
		//获取当前用户的收获地址
		List<OrderAddress> address = orderAddressService.userTakeDeliveryAddress(user);
		//获取商品信息
		goods = goodsService.selectInfo(goods);
		
		model.addAttribute("amount", amount);
		model.addAttribute("goods", goods);
		model.addAttribute("address", address);
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
