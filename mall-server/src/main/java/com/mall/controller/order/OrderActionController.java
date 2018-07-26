package com.mall.controller.order;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.controller.AbstractController;
import com.mall.entity.order.OrderAction;
import com.mall.message.ProcessResult;
import com.mall.service.order.OrderActionService;

/**
 * 订单操作异步请求
 * @author Jang
 *
 */
@Controller
@RequestMapping("/admin/order/action")
public class OrderActionController extends AbstractController{
	@Resource
	private OrderActionService orderActionService;
	@RequestMapping("/add")
	@ResponseBody
	public ProcessResult<OrderAction> toAddOrderAction(OrderAction orderAction){
		ProcessResult<OrderAction> res=new ProcessResult<OrderAction>();
		try {
			orderActionService.insertSelective(orderAction);
			res=ProcessResult.success(res);
			return res;
		} catch (Exception e) {
			logger.error("操作订单失败！订单号："+orderAction.getOrder_number());
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return res;
	}

}
