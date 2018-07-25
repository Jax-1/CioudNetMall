package com.mall.controller.order;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.yaml.snakeyaml.constructor.BaseConstructor;

import com.mall.entity.order.Order;
import com.mall.message.SystemCode;
import com.mall.service.order.OrderService;
import com.mall.service.sys.CacheService;
import com.mall.util.PageResult;


@Controller 
@RequestMapping("/admin/order")
public class AdminOrderController  extends BaseConstructor{
	@Resource
	private CacheService cacheService;
	@Resource
	private OrderService orderService;
	
	@RequestMapping("/list")
	public String toOrderList(Model model ,Order order ,PageResult<Order> list) {
		//获取订单列表
		int pageSize  =  Integer.parseInt(cacheService.getCache(SystemCode.PAGE).get(SystemCode.GOODS_PAGE));
		list.setPageSize(pageSize);
		list =orderService.queryByPageFront(list,order);
		model.addAttribute("orderentity", order);
		model.addAttribute("list", list);
		model.addAttribute("page", "admin/order/order_list");
		model.addAttribute("order", "nav-item start active open");
		return "admin/index";
	}
	@RequestMapping("/detail")
	public String toOrderDetail(Model model,Order order) {
		order=orderService.selectInfo(order);
		model.addAttribute("order", order);
		model.addAttribute("page", "admin/order/order_detail");
		model.addAttribute("order", "nav-item start active open");
		return "admin/index";
	}
	

}
