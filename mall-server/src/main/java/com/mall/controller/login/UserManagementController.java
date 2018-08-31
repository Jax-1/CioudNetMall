package com.mall.controller.login;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.controller.AbstractController;
import com.mall.dao.login.UserMapper;
import com.mall.entity.inventory.InventoryDeivery;
import com.mall.entity.login.User;
import com.mall.entity.login.UserInfo;
import com.mall.entity.order.Order;
import com.mall.entity.order.OrderAddress;
import com.mall.entity.order.OrderServe;
import com.mall.message.ProcessResult;
import com.mall.message.SystemCode;
import com.mall.service.inventory.InventoryDeiveryService;
import com.mall.service.login.UserInfoService;
import com.mall.service.login.UserLoginService;
import com.mall.service.order.OrderAddressService;
import com.mall.service.order.OrderServeService;
import com.mall.service.order.OrderService;
import com.mall.service.sys.CacheService;
import com.mall.util.DateFormatUtil;
import com.mall.util.MD5Util;
import com.mall.util.PageResult;
import com.mall.util.SessionUtil;
import com.mall.util.Validate;

/**
 * 用户信息管理
 * @author Jang
 *
 */
@Controller
@RequestMapping("/mall/user")
public class UserManagementController extends AbstractController{
	@Resource
	private UserLoginService userLoginService;
	@Resource
	private UserInfoService userInfoService;
	@Resource
	private OrderAddressService orderAddressService;
	@Resource
	private OrderService orderService;
	@Resource
	private CacheService cacheService;
	@Resource
	private OrderServeService orderServeService;
	@Resource
	private InventoryDeiveryService inventoryDeiveryService;
	@Resource
	private UserMapper userMapper;
	/**
	 * 跳转用户管理界面
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/manager")
	public String toUserManager(Model model,HttpServletRequest request) {
		
		model.addAttribute("page", "mall/login/my_center");
		model.addAttribute("manager", "info");
		return "mall/index";
	}
	/**
	 * 跳转用户密码修改验证
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/modify_pwd_verify")
	public String toUserModifyPwdVardate(Model model,HttpServletRequest request) {
		//文件服务器路径
		Map<String, String> cache = cacheService.getCache(SystemCode.FILE_SERVICE);
		String url=cache.get(SystemCode.FILE_SERVICE_URL);
		String port=cache.get(SystemCode.FILE_SERVICE_PORT);
		String filePath=cache.get(SystemCode.FILE_SERVICE_FILES_PATH);
		String fileUrlPrefix=url+":"+port+"/"+filePath;
		model.addAttribute("fileServicePath", fileUrlPrefix);
		model.addAttribute("page", "mall/login/my_center");
		model.addAttribute("manager", "modify_pwd_verify");
		return "mall/index";
	}
	/**
	 * 用户收货地址管理
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/address")
	public String toUserModifyAddress(Model model,HttpServletRequest request) {
		User user = SessionUtil.getUser(request);
		//获取当前用户的收获地址
		List<OrderAddress> address = orderAddressService.userTakeDeliveryAddress(user);
		model.addAttribute("address", address);
		model.addAttribute("page", "mall/login/my_center");
		model.addAttribute("manager", "address");
		return "mall/index";
	}
	/**
	 * 用户订单管理
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/order")
	public String toUserOrder(Model model,HttpServletRequest request,PageResult<Order> list,Order order) {
		
		User user = SessionUtil.getUser(request);
		if(!Validate.notNull(order)) {
			order=new Order();
		}
		logger.info("获取用户订单列表！Username="+user.getUser_name());
		order.setUser_id(user.getUser_name());
		list =orderService.queryByPageFront(list,order);
		//文件服务器路径
		Map<String, String> cache = cacheService.getCache(SystemCode.FILE_SERVICE);
		String url=cache.get(SystemCode.FILE_SERVICE_URL);
		String port=cache.get(SystemCode.FILE_SERVICE_PORT);
		String filePath=cache.get(SystemCode.FILE_SERVICE_FILES_PATH);
		String fileUrlPrefix=url+":"+port+"/"+filePath;
		model.addAttribute("fileServicePath", fileUrlPrefix);
		model.addAttribute("orderentity", order);
		model.addAttribute("list", list);
		model.addAttribute("page", "mall/login/my_center");
		model.addAttribute("manager", "order");
		return "mall/index";
	}
	/**
	 * 用户订单详情
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("/order/details")
	public String toUserOrderDetails(Order order,Model model) {
		logger.info("获取用户订单详情！order="+order.getOrder_number());
		order=orderService.selectInfo(order);
		//文件服务器路径
		Map<String, String> cache = cacheService.getCache(SystemCode.FILE_SERVICE);
		String url=cache.get(SystemCode.FILE_SERVICE_URL);
		String port=cache.get(SystemCode.FILE_SERVICE_PORT);
		String filePath=cache.get(SystemCode.FILE_SERVICE_FILES_PATH);
		String fileUrlPrefix=url+":"+port+"/"+filePath;
		
		//获取发货单信息
		InventoryDeivery inventoryDeivery = new InventoryDeivery();
		inventoryDeivery.setOrder_number(order.getOrder_number());
		inventoryDeivery=inventoryDeiveryService.selectByNumber(inventoryDeivery);
		model.addAttribute("deivery", inventoryDeivery);
				
		model.addAttribute("fileServicePath", fileUrlPrefix);
		model.addAttribute("entity", order);
		model.addAttribute("page", "mall/login/my_center");
		model.addAttribute("manager", "orderdetails");
		return "mall/index";
		
	}
	/**
	 * 校验验证信息
	 * @param model
	 * @param request
	 * @param imgcode
	 * @param phonecode
	 * @return
	 */
	@RequestMapping("/checkCode")
	@ResponseBody
	public ProcessResult<User> checkCode(Model model,HttpServletRequest request,String imgcode,String phonecode) {
		/**
		 * 校验验证信息
		 */
		ProcessResult<User> process=new ProcessResult<User>();
		if(!Validate.notNull(imgcode)||!Validate.notNull(phonecode)) {
			process.setMsg("验证码不得为空！");
			return process;
		}
		// 验证图片验证码
        String sessionCode = request.getSession().getAttribute("imgcode").toString();
        if (Validate.notNull(imgcode)&&Validate.notNull(sessionCode)) {
            if (!imgcode.equalsIgnoreCase(sessionCode)) {
            	process.setMsg("验证失败！");
            	return process;
            } 
        }else {
        	process.setMsg("验证失败！");
        	return process;
        } 
        //验证手机验证码
        String sessionPhoneCode = request.getSession().getAttribute("phonecode").toString();
        if (Validate.notNull(phonecode)&&Validate.notNull(sessionPhoneCode)) {
            if (!phonecode.equals(sessionPhoneCode)) {
            	process.setMsg("手机验证失败！");
            	return process;
            } 
        }else {
        	process.setMsg("手机验证失败！");
        	return process;
        } 
        logger.info("用户注册，验证码校验成功！");
        //将校验结果存入session
        request.getSession().setAttribute("checkCode", true);
        process.setRes(SystemCode.SUCCESS);
        return process;
	}
	
	/**
	 * 跳转用户密码修改
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/modify_pwd")
	public String toUserModifyPwd(Model model,HttpServletRequest request) {
		logger.info("用户更改密码校验："+request.getSession().getAttribute("checkCode"));
		if(request.getSession().getAttribute("checkCode")==null||
				!Boolean.valueOf(request.getSession().getAttribute("checkCode").toString()).booleanValue()) {
			//未经验证
			model.addAttribute("page", "mall/base/index_body");
			return "mall/index";
			
		}
		model.addAttribute("page", "mall/login/my_center");
		model.addAttribute("manager", "modify_pwd");
		return "mall/index";
	}
	/**
	 * 更改密码
	 * @param model
	 * @param request
	 * @param password
	 * @return
	 */
	@RequestMapping("/modify")
	public String toChengePwd(Model model,HttpServletRequest request,String password) {
		logger.info("用户更改密码校验："+request.getSession().getAttribute("checkCode"));
		if(request.getSession().getAttribute("checkCode")==null||
				!Boolean.valueOf(request.getSession().getAttribute("checkCode").toString()).booleanValue()) {
			//未经验证
			model.addAttribute("page", "mall/base/index_body");
			return "mall/index";
			
		}
		//更改密码
		User user = SessionUtil.getUser(request);
		String rand = MD5Util.getRand();
		user.setRand(rand);
		user.setPassword(MD5Util.encoder(password,rand));
		try {
			userLoginService.updateByPrimaryKeySelective(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//清除登陆信息
		SessionUtil.setUser(request, null);
		return "redirect:/user/login.do";
	}
	/**
	 * 更改用户信息
	 * @param model
	 * @param request
	 * @param userinfo
	 * @return
	 */
	@RequestMapping("/chengeuserinfo")
	@ResponseBody
	public ProcessResult<User> chengeUserInfo(HttpServletRequest request,UserInfo userInfo) {
		ProcessResult<User> res=new ProcessResult<User>();
		try {
			
			/**
			 * 重新写入用户信息
			 */
			
			User user = SessionUtil.getUser(request);
			userInfo.setUpdate_time(DateFormatUtil.getDate());
			user.setUserInfo(userInfo);
			userInfo.setId(user.getUserinfo_id());
			userInfo.setUser_name(user.getUser_name());
			userInfo.setCreate_at(user.getUserInfo().getCreate_at());
			logger.info("更改用户信息："+userInfo.getUser_name());
			
			userInfoService.updateByPrimaryKey(userInfo);
			user=userMapper.Login(user);
			SessionUtil.setUser(request, user);
			res.setRes(SystemCode.SUCCESS);
			res.setMsg("用户信息修改成功");
		} catch (Exception e) {
			//更新用户信息失败
			return res;
		}
		
		return res;
	}
	
	
	@RequestMapping("/service/details")
	public String toServiceDetails(Model model,OrderServe orderServe) {
		logger.info("获取用户服务单详情！orderServe="+orderServe.getService_number());
		//获取售后单信息
		orderServe=orderServeService.selectInfo(orderServe);
		Order order=new Order();
		order.setOrder_number(orderServe.getOrder_number());
		order=orderService.selectInfo(order);
		//获取售后单信息
		orderServe=orderServeService.selectInfo(orderServe);
		//文件服务器路径
		Map<String, String> cache = cacheService.getCache(SystemCode.FILE_SERVICE);
		String url=cache.get(SystemCode.FILE_SERVICE_URL);
		String port=cache.get(SystemCode.FILE_SERVICE_PORT);
		String filePath=cache.get(SystemCode.FILE_SERVICE_FILES_PATH);
		String fileUrlPrefix=url+":"+port+"/"+filePath;
		model.addAttribute("fileServicePath", fileUrlPrefix);
		model.addAttribute("entity", order);
		model.addAttribute("orderServe", orderServe);
		model.addAttribute("page", "mall/login/my_center");
		model.addAttribute("manager", "serviceDetails");
		return "mall/index";
		
	}
	@RequestMapping("/service/list")
	public String toServiceList(Model model,OrderServe orderServe,PageResult<OrderServe> list,HttpServletRequest request) {
		
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
		
		//文件服务器路径
		Map<String, String> cache = cacheService.getCache(SystemCode.FILE_SERVICE);
		String url=cache.get(SystemCode.FILE_SERVICE_URL);
		String port=cache.get(SystemCode.FILE_SERVICE_PORT);
		String filePath=cache.get(SystemCode.FILE_SERVICE_FILES_PATH);
		String fileUrlPrefix=url+":"+port+"/"+filePath;
		model.addAttribute("fileServicePath", fileUrlPrefix);
		model.addAttribute("list", list);
		model.addAttribute("page", "mall/login/my_center");
		model.addAttribute("manager", "serviceList");
		return "mall/index";
		
	}
	@RequestMapping("/service/edit")
	public String toServiceEdit(Order order,Model model) {
		logger.info("获取用户服务单编辑视图！order="+order.getOrder_number());
		order=orderService.selectInfo(order);
		//文件服务器路径
		Map<String, String> cache = cacheService.getCache(SystemCode.FILE_SERVICE);
		String url=cache.get(SystemCode.FILE_SERVICE_URL);
		String port=cache.get(SystemCode.FILE_SERVICE_PORT);
		String filePath=cache.get(SystemCode.FILE_SERVICE_FILES_PATH);
		String fileUrlPrefix=url+":"+port+"/"+filePath;
		model.addAttribute("fileServicePath", fileUrlPrefix);
		model.addAttribute("entity", order);
		model.addAttribute("page", "mall/login/my_center");
		model.addAttribute("manager", "serviceEdit");
		return "mall/index";
		
	}

}
