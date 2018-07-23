package com.mall.controller.order;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.yaml.snakeyaml.constructor.BaseConstructor;


@Controller 
@RequestMapping("/admin/order")
public class AdminOrderController  extends BaseConstructor{
	@RequestMapping("/list")
	public String toOrderList(Model model) {
		model.addAttribute("page", "admin/order/order_list");
		model.addAttribute("order", "nav-item start active open");
		return "admin/index";
	}
	@RequestMapping("/detail")
	public String toOrderDetail(Model model) {
		model.addAttribute("page", "admin/order/order_detail");
		model.addAttribute("order", "nav-item start active open");
		return "admin/index";
	}
	

}
