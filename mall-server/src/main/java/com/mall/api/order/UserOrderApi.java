package com.mall.api.order;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mall.api.BaseAPI;
import com.mall.entity.login.User;
import com.mall.entity.order.Order;
import com.mall.message.SystemCode;
import com.mall.service.order.OrderService;
import com.mall.util.PageResult;
import com.mall.util.SessionUtil;
import com.mall.util.Validate;

@Controller
@RequestMapping("api/user/order")
public class UserOrderApi extends BaseAPI {
	@Resource
	private OrderService orderService;
	/**
	 * 用户订单管理
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/order")
	public PageResult<Order> toUserOrder(Model model,HttpServletRequest request,PageResult<Order> list,Order order) {
		
		User user = SessionUtil.getUser(request);
		if(!Validate.notNull(order)) {
			order=new Order();
		}
		logger.info("获取用户订单列表！Username="+user.getUser_name());
		order.setUser_id(user.getUser_name());
		list =orderService.queryByPageFront(list,order);
		return list;
	}
	
	

}
