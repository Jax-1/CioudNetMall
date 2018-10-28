package com.mall.api.login;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.api.BaseAPI;
import com.mall.entity.login.User;
import com.mall.entity.order.Order;
import com.mall.entity.order.OrderServe;
import com.mall.message.SystemCode;
import com.mall.service.order.OrderServeService;
import com.mall.service.order.OrderService;
import com.mall.service.sys.CacheService;
import com.mall.util.PageResult;
import com.mall.util.SessionUtil;

/**
 * 售后服务
 * @author Jang
 *
 */
@Controller
@RequestMapping("api/user/service")
public class UserServiceApi extends BaseAPI {
	@Resource
	private OrderService orderService;
	@Resource
	private CacheService cacheService;
	@Resource
	private OrderServeService orderServeService;
	
	/**
	 * 售后服务单详情
	 * @param model
	 * @param orderServe
	 * @return
	 */
	
	@RequestMapping("details")
	@ResponseBody
	public OrderServe toServiceDetails(OrderServe orderServe) {
		logger.info("获取用户服务单详情！orderServe="+orderServe.getService_number());
		//获取售后单信息
		orderServe=orderServeService.selectInfo(orderServe);
		Order order=new Order();
		order.setOrder_number(orderServe.getOrder_number());
		order=orderService.selectInfo(order);
		//获取售后单信息
		orderServe=orderServeService.selectInfo(orderServe);
		
		return orderServe;
		
	}
	/**
	 * 售后服务单列表
	 * @param model
	 * @param orderServe
	 * @param list
	 * @param request
	 * @return
	 */
	@RequestMapping("list")
	@ResponseBody
	public PageResult<OrderServe> getServiceList(Model model,OrderServe orderServe,PageResult<OrderServe> list,HttpServletRequest request) {
		
		User user = SessionUtil.getUser(request);
		orderServe.setCreate_at(user.getUser_name());
		logger.info("获取用户服务单列表！");
		list=orderServeService.queryByPageFront(list, orderServe);
		for(OrderServe os:list.getDataList()) {
			Order order=new Order();
			order.setOrder_number(os.getOrder_number());
			order=orderService.selectInfo(order);
			os.setOrder(order);
		}
		return list;
		
	}
}
