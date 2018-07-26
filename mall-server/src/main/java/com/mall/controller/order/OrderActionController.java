package com.mall.controller.order;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.controller.AbstractController;
import com.mall.entity.login.Admin;
import com.mall.entity.order.OrderAction;
import com.mall.message.ProcessResult;
import com.mall.service.order.OrderActionService;
import com.mall.util.DateFormatUtil;
import com.mall.util.SessionUtil;

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
	public ProcessResult<OrderAction> toAddOrderAction(OrderAction orderAction,HttpServletRequest request){
		ProcessResult<OrderAction> res=new ProcessResult<OrderAction>();
		logger.info("创建订单操作记录！");
		try {
			Admin adminUser = SessionUtil.getAdminUser(request);
			orderAction.setAction_user(adminUser.getAdmin_name());
			orderAction.setLog_time(DateFormatUtil.getDate());
			orderActionService.insertSelective(orderAction);
			res=ProcessResult.success(res);
			res.setObj(orderAction);
			return res;
		} catch (Exception e) {
			logger.error("操作订单失败！订单号："+orderAction.getOrder_number());
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return res;
	}

}
