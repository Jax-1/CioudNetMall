package com.mall.controller.order;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mall.controller.AbstractController;
import com.mall.entity.cms.AuthorWithBLOBs;
import com.mall.entity.goods.Goods;
import com.mall.entity.goods.GoodsPrice;
import com.mall.entity.login.User;
import com.mall.entity.order.Order;
import com.mall.entity.order.OrderAddress;
import com.mall.entity.order.OrderDetails;
import com.mall.entity.payment.PaymentMethod;
import com.mall.message.SystemCode;
import com.mall.service.cms.AuthorWithBLOBsService;
import com.mall.service.goods.GoodsService;
import com.mall.service.order.OrderAddressService;
import com.mall.service.order.OrderDetailsService;
import com.mall.service.order.OrderService;
import com.mall.service.payment.PaymentMethodService;
import com.mall.service.sys.CacheService;
import com.mall.util.DateFormatUtil;
import com.mall.util.ProcessOrderUtil;
import com.mall.util.SessionUtil;
import com.mall.util.Validate;

/**
 * 订单
 * @author Jang
 *
 */
@Controller
@RequestMapping("/mall/order")
public class OrderController extends AbstractController{
	@Resource
	private OrderAddressService orderAddressService;
	@Resource
	private GoodsService goodsService;
	@Resource
	private PaymentMethodService paymentMethodService;
	@Resource
	private CacheService cacheService;
	@Resource
	private AuthorWithBLOBsService authorWithBLOBsService;
	@Resource
	private OrderDetailsService orderDetailsService;
	@Resource
	private OrderService orderService;
	/**
	 * 订单界面
	 * @param model
	 * @param goods
	 * @param request
	 * @param amount
	 * @return
	 */
	@RequestMapping("")
	public String toOrder(Model model,Goods goods,HttpServletRequest request ,Integer amount,Order order) {
		User user = SessionUtil.getUser(request);
		//获取当前用户的收获地址
		List<OrderAddress> address = orderAddressService.userTakeDeliveryAddress(user);
		if(Validate.notNull(order)&&Validate.notNull(order.getOrder_number())) {
			//加载单据信息
			order=orderService.selectInfo(order);
			for(OrderDetails o:order.getOrderDetailsList()) {
				goods.setGoods_id(o.getGoods_id());
			}
		}
		//获取商品信息
		logger.info("获取商品信息："+goods.getGoods_id());
		goods = goodsService.selectInfo(goods);
		if(Validate.notNull(goods.getGoodsInfo().getAuth_id())) {
			//查询商品作家信息
			AuthorWithBLOBs a=new AuthorWithBLOBs();
			a.setId(goods.getGoodsInfo().getAuth_id());
			a = authorWithBLOBsService.selectInfo(a);
			goods.setAuth(a);
		}
		
		
		//获取支付方式
		List<PaymentMethod> payment = paymentMethodService.getPaymentMethod();
		//文件服务器路径
		Map<String, String> cache = cacheService.getCache(SystemCode.FILE_SERVICE);
		String url=cache.get(SystemCode.FILE_SERVICE_URL);
		String port=cache.get(SystemCode.FILE_SERVICE_PORT);
		String filePath=cache.get(SystemCode.FILE_SERVICE_FILES_PATH);
		String fileUrlPrefix=url+":"+port+"/"+filePath;
		
		model.addAttribute("order", order);
		model.addAttribute("fileServicePath", fileUrlPrefix);
		model.addAttribute("payment", payment);
		model.addAttribute("amount", amount);
		model.addAttribute("goods", goods);
		model.addAttribute("address", address);
		model.addAttribute("page", "mall/order/order");
		return "mall/index";
		
	}
	/**
	 * 支付界面，选择支付方式，
	 * 初次：生成订单
	 * 已有订单进入：加载订单
	 * @param model
	 * @param order
	 * @param orderDetails
	 * @param request
	 * @return
	 */
	@RequestMapping("/pay")
	public String toOrderPay(Model model,Order order,OrderDetails orderDetails,HttpServletRequest request) {
		//
		if(Validate.notNull(order.getOrder_number())) {
			//历史订单支付
			logger.info("历史订单支付！");
		}else {
			logger.info("生成订单支付！");
			logger.info("支付方式："+order.getPayment_id());
			//订单号为空，初次进入
			order=Order.init(order, request);
			order.setOrder_number(ProcessOrderUtil.processOrderNumber(SystemCode.DEV_PC, SystemCode.BUSINESS_MALL, SessionUtil.getUser(request)));
			try {
				orderService.insertSelective(order);
			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.info("创建订单："+order.getOrder_number());
			orderDetails.setCreate_time(DateFormatUtil.getDate());
			orderDetails.setOrder_id(order.getOrder_id());
			orderDetails.setOrder_number(order.getOrder_number());
			try {
				orderDetailsService.insertSelective(orderDetails);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		//获取支付方式
		List<PaymentMethod> payment = paymentMethodService.getPaymentMethod();
		
		model.addAttribute("payment", payment);
		model.addAttribute("order", order);
		model.addAttribute("orderDetails", orderDetails);
		model.addAttribute("page", "mall/order/order_pay");
		return "mall/index";
		
	}
	
	@RequestMapping("/real")
	public String toOrderReal(Model model,Order orderin) {
		logger.info("订单支付！订单号："+orderin.getOrder_number());
		Order order = orderService.selectInfo(orderin);
		BigDecimal total_amount=BigDecimal.ZERO;
		if(Validate.notNull(order)) {
			List<OrderDetails> orderDetailsList = order.getOrderDetailsList();
			for(OrderDetails orderDetails:orderDetailsList) {
				Goods goods=new Goods();
				goods.setGoods_id(orderDetails.getGoods_id());
				goods=goodsService.selectInfo(goods);
				//校验订单与商品价格
				GoodsPrice goodsPrice = goods.getGoodsPrice();
				logger.info("是否优惠订单："+goodsPrice.getSale());
				if("Y".equals(goodsPrice.getSale())) {
					//优惠价格
					total_amount=total_amount.add(goodsPrice.getSale_price());
				}else {
					total_amount=total_amount.add(goodsPrice.getRetail_price());
				}
				
			}
			//订单总金额+邮费
			total_amount=total_amount.add(order.getPostage_amount());
			
		}
		logger.info("订单金额："+order.getTotal_amount()+"  校验金额："+total_amount);
		if(total_amount.compareTo(order.getTotal_amount())==0) {
			//金额正确
			logger.info("订单金额校验完成！订单号："+order.getOrder_number());
			model.addAttribute("order", order);
			
		}else {
			//金额有误
			logger.info("订单金额校验完成！金额有误！订单号："+order.getOrder_number());
			model.addAttribute("order", order);
		}
		model.addAttribute("page", "mall/order/order_real");
		return "mall/index";
		
	}

}
