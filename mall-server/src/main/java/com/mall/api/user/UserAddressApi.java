package com.mall.api.user;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.api.BaseAPI;
import com.mall.entity.order.OrderAddress;
import com.mall.entity.user.User;
import com.mall.message.ProcessResult;
import com.mall.service.order.OrderAddressService;
import com.mall.util.DateFormatUtil;
import com.mall.util.SessionUtil;
import com.mall.util.Validate;

@Controller
@RequestMapping("api/user/address")
public class UserAddressApi extends BaseAPI {
	@Resource
	private OrderAddressService orderAddressService;
	
	/**
	 * 用户收货地址管理
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/address")
	@ResponseBody
	public List<OrderAddress> toUserModifyAddress(Model model,HttpServletRequest request) {
		User user = SessionUtil.getUser(request);
		//获取当前用户的收获地址
		List<OrderAddress> address = orderAddressService.userTakeDeliveryAddress(user);
		
		return address;
	}
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
			logger.info("删除用户收获地址！");
			orderAddressService.chengeStatus(orderAddress);
			res=ProcessResult.success(res);
			return res;
		} catch (Exception e) {
			
			logger.error("删除用户收货地址失败！id="+orderAddress.getId());
			logger.error(e.getMessage());
			e.printStackTrace();
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
			if(Validate.notNull(orderAddress.getId())) {
				logger.info("修改用户收货地址！");
				orderAddressService.updateByPrimaryKeySelective(orderAddress);
			}else {
				logger.info("添加用户收货地址！");
				orderAddress.setUser_id(SessionUtil.getUser(request).getUser_name());
				orderAddress.setCreate_time(DateFormatUtil.getDate());
				orderAddressService.insertSelective(orderAddress);
			}
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
	@PostMapping("/default")
	@ResponseBody
	public ProcessResult<OrderAddress> changeAddressDefault(OrderAddress orderAddress,HttpServletRequest request){
		ProcessResult<OrderAddress> res=new ProcessResult<OrderAddress>();
		User user = SessionUtil.getUser(request);
//		User user = new User();
//		user.setUser_name("13260631321");
		if( orderAddressService.changeAddressDefault(orderAddress, user)) {
			res=ProcessResult.success(res);
			return res;
		}
		res.setMsg("设置默认收货地址失败！");
		return res;
	}
	/**
	 * 用户收货地址管理
	 * @param model
	 * @param request
	 * @return
	 */
	@GetMapping("/default")
	@ResponseBody
	public OrderAddress getDefaultAddress(HttpServletRequest request) {
		User user = SessionUtil.getUser(request);
		//获取当前用户的收获地址
		List<OrderAddress> addressList = orderAddressService.userTakeDeliveryAddress(user);
		for(OrderAddress address:addressList) {
			if("Y".equals(address.getThedefault())) {
				return address;
			}
		}
		return new OrderAddress();
		
		
	}
	

}
