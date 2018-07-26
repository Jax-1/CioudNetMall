package com.mall.controller.order;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.controller.AbstractController;
import com.mall.entity.order.Order;
import com.mall.message.ProcessResult;
import com.mall.service.order.OrderService;

@Controller
@RequestMapping("/admin/order")
public class OrderController extends AbstractController{
	@Resource
	private OrderService orderService;
	
	@RequestMapping("/chengeStatus")
	@ResponseBody
	public ProcessResult<Order> chengeStatus(Order order){
		ProcessResult<Order> res=new ProcessResult<Order>();
		logger.info("修改订单状态！PRDERNUMBER:"+order.getOrder_number());
		int update = orderService.updateOrderStatus(order);
		if(update>0) {
			res=ProcessResult.success(res);
			return res;
		}
		return res;
		
	}

}
