package com.mall.controller.order;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.controller.AbstractController;
import com.mall.entity.order.OrderAddress;
import com.mall.message.ProcessResult;
import com.mall.service.order.OrderAddressService;
import com.mall.util.DateFormatUtil;
import com.mall.util.SessionUtil;

@Controller
@RequestMapping("/mall/order")
public class OrderAddressController  extends AbstractController{
	@Resource
	private OrderAddressService orderAddressService;
	/**
	 * 删除收货信息
	 * @param orderAddress
	 * @return
	 */
	@PostMapping("/address/delete")
	@ResponseBody
	public ProcessResult<OrderAddress> delete(OrderAddress orderAddress){
		ProcessResult<OrderAddress> res=new ProcessResult<OrderAddress>();
		try {
			orderAddressService.delete(orderAddress);
			res=ProcessResult.success(res);
			return res;
		} catch (Exception e) {
			logger.error("删除用户收货地址失败！id="+orderAddress.getId());
			logger.error(e.getMessage());
		}
		res.setMsg("删除收货地址失败！");
		return res;
		
	}
	/**
	 * 添加收货信息
	 * @param orderAddress
	 * @return
	 */
	@PostMapping("/address/add")
	@ResponseBody
	public ProcessResult<OrderAddress> add(OrderAddress orderAddress,HttpServletRequest request){
		ProcessResult<OrderAddress> res=new ProcessResult<OrderAddress>();
		try {
			orderAddress.setUser_id(SessionUtil.getUser(request).getUser_name());
			orderAddress.setCreate_time(DateFormatUtil.getDate());
			orderAddressService.insertSelective(orderAddress);
			res=ProcessResult.success(res);
			res.setObj(orderAddress);
			return res;
		} catch (Exception e) {
			logger.error("添加用户收货地址失败！id="+orderAddress.getId());
			logger.error(e.getMessage());
		}
		res.setMsg("添加收货地址失败！");
		return res;	
	}

}
