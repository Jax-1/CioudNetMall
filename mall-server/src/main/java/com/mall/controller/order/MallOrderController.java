package com.mall.controller.order;

import java.math.BigDecimal;
import java.util.ArrayList;
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
import com.mall.entity.order.Order;
import com.mall.entity.order.OrderAddress;
import com.mall.entity.order.OrderDetails;
import com.mall.entity.payment.PaymentMethod;
import com.mall.entity.user.User;
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
public class MallOrderController extends AbstractController{
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
	private Order order2;
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
		List<Goods> list=new ArrayList<Goods>();
		logger.info("购买商品："+goods.getGoods_id());
		//获取当前用户的收获地址
		List<OrderAddress> address = orderAddressService.userTakeDeliveryAddress(user);
		//单商品购买
		if(Validate.notNull(goods)&& goods.getGoods_id()!=null) {
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
			goods.setAmount(amount);
			list.add(goods);
		}
		//多商品生成订单
		
//		if(Validate.notNull(order)&&Validate.notNull(order.getOrder_number())) {
//			//加载单据信息
//			order=orderService.selectInfo(order);
//			for(OrderDetails o:order.getOrderDetailsList()) {
//				goods.setGoods_id(o.getGoods_id());
//			}
//		}
		//已有订单支付
		if(Validate.notNull(order)&&order.getOrder_number()!=null) {
			order=orderService.selectInfo(order);
			for(OrderDetails g:order.getOrderDetailsList()) {
				Goods goodsSelect =new Goods();
				goodsSelect.setGoods_id(g.getGoods_id());
				goodsSelect = goodsService.selectInfo(goodsSelect);
				if(Validate.notNull(goodsSelect.getGoodsInfo().getAuth_id())) {
					//查询商品作家信息
					AuthorWithBLOBs a=new AuthorWithBLOBs();
					a.setId(goodsSelect.getGoodsInfo().getAuth_id());
					a = authorWithBLOBsService.selectInfo(a);
					goodsSelect.setAuth(a);
				}
				goodsSelect.setAmount(g.getNum());
				list.add(goodsSelect);
			}
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
		model.addAttribute("list", list);
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
	public String toOrderPay(Model model,Order order,HttpServletRequest request) {
		//
		if(Validate.notNull(order.getOrder_number())) {
			Order orderUpdate=order;
			order=orderService.selectInfo(order);
			//历史订单支付
			logger.info("历史订单支付！"+order.getOrder_number());
			BigDecimal total_amount=BigDecimal.ZERO;
			BigDecimal postage_amount=BigDecimal.ZERO;
			BigDecimal discount_amount=BigDecimal.ZERO;
			logger.info("payment_id:"+order.getPayment_id());
			//更新订单单品商品信息
			for(OrderDetails orderDetails:order.getOrderDetailsList()) {
				BigDecimal details_amount=BigDecimal.ZERO;
				BigDecimal num = new BigDecimal(orderDetails.getNum()); 
				Goods goods=new Goods();
				goods.setGoods_id(orderDetails.getGoods_id());
				logger.info("获取商品信息："+goods.getGoods_id());
				goods = goodsService.selectInfo(goods);
				
				for(OrderDetails newOrderDetails:orderUpdate.getOrderDetailsList()) {
					if(orderDetails.getGoods_id().equals(newOrderDetails.getGoods_id())) {
						orderDetails.setNum(newOrderDetails.getNum());
					}
				}
				
				orderDetails.setOrder_id(order.getOrder_id());
				orderDetails.setOrder_number(order.getOrder_number());
				//快递名
				orderDetails.setUnit_name(goods.getGoodsInfo().getExt2());
				orderDetails.setPrice_id(goods.getGoods_price_id());
				orderDetails.setGoods_id(goods.getGoods_id());
				orderDetails.setGoods_name(goods.getGoods_name());
				orderDetails.setImage(goods.getImage());
				logger.info("是否优惠商品："+goods.getGoodsPrice().getSale());
				if("Y".equals(goods.getGoodsPrice().getSale())) {
					logger.info("优惠商品价格：" +goods.getGoodsPrice().getSale_price());
					//优惠价格
					orderDetails.setUnit_price(goods.getGoodsPrice().getSale_price());
					//计算优惠总价
					BigDecimal subtract = goods.getGoodsPrice().getRetail_price().subtract(goods.getGoodsPrice().getSale_price());  //单件优惠
					discount_amount=discount_amount.add(subtract.multiply(num));
				}else {
					orderDetails.setUnit_price(goods.getGoodsPrice().getRetail_price());
				}
				details_amount=orderDetails.getUnit_price().multiply(num);
				orderDetails.setDetails_amount(details_amount);
				//计算总邮费
				//不包邮状态
				if("N".equals(goods.getGoodsInfo().getExt1())) {
					postage_amount=postage_amount.add(new BigDecimal(goods.getGoodsInfo().getExt3()));
				}
				//计算商品总价,加单品总价
				total_amount=total_amount.add(details_amount);
				try {
					orderDetailsService.updateByPrimaryKeySelective(orderDetails);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			//计算商品总价,加邮费总额
			total_amount=total_amount.add(postage_amount);
			order.setDiscount_amount(discount_amount);
			order.setTotal_amount(total_amount);
			order.setPostage_amount(postage_amount);
			//更改支付方式、收货Id、备注
			order.setReceive_id(orderUpdate.getReceive_id());
			order.setPayment_id(orderUpdate.getPayment_id());
			order.setComment(orderUpdate.getComment());
			try {
				orderService.updateByPrimaryKeySelective(order);
			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.info("更新订单信息："+order.getOrder_number());
		}else {
			logger.info("生成订单支付！");
			logger.info("支付方式："+order.getPayment_id());
			//订单号为空，初次进入
			order=Order.init(order, request);
			//创建订单号
			while(true){
				String order_number=ProcessOrderUtil.processOrderNumber(SystemCode.DEV_PC, SystemCode.BUSINESS_MALL, SessionUtil.getUser(request));
				Order checkOrder = orderService.selectByPrimaryKey(order_number);
				if(!Validate.notNull(checkOrder)) {
					order.setOrder_number(order_number);
					break;
				}
			}
			BigDecimal total_amount=BigDecimal.ZERO;
			BigDecimal postage_amount=BigDecimal.ZERO;
			BigDecimal discount_amount=BigDecimal.ZERO;
			logger.info("payment_id:"+order.getPayment_id());
			//创建订单商品信息
			for(OrderDetails orderDetails:order.getOrderDetailsList()) {
				BigDecimal details_amount=BigDecimal.ZERO;
				BigDecimal num = new BigDecimal(orderDetails.getNum()); 
				Goods goods=new Goods();
				goods.setGoods_id(orderDetails.getGoods_id());
				logger.info("获取商品信息："+goods.getGoods_id());
				goods = goodsService.selectInfo(goods);
				
				orderDetails.setCreate_time(DateFormatUtil.getDate());
				orderDetails.setOrder_id(order.getOrder_id());
				orderDetails.setOrder_number(order.getOrder_number());
				//快递名
				orderDetails.setUnit_name(goods.getGoodsInfo().getExt2());
				orderDetails.setPrice_id(goods.getGoods_price_id());
				orderDetails.setGoods_id(goods.getGoods_id());
				orderDetails.setGoods_name(goods.getGoods_name());
				orderDetails.setImage(goods.getImage());
				logger.info("是否优惠商品："+goods.getGoodsPrice().getSale());
				if("Y".equals(goods.getGoodsPrice().getSale())) {
					logger.info("优惠商品价格：" +goods.getGoodsPrice().getSale_price());
					//优惠价格
					orderDetails.setUnit_price(goods.getGoodsPrice().getSale_price());
					//计算优惠总价
					BigDecimal subtract = goods.getGoodsPrice().getRetail_price().subtract(goods.getGoodsPrice().getSale_price());  //单件优惠
					discount_amount=discount_amount.add(subtract.multiply(num));
				}else {
					orderDetails.setUnit_price(goods.getGoodsPrice().getRetail_price());
				}
				details_amount=orderDetails.getUnit_price().multiply(num);
				orderDetails.setDetails_amount(details_amount);
				//计算总邮费
				//不包邮状态
				if("N".equals(goods.getGoodsInfo().getExt1())&&Validate.notNull(goods.getGoodsInfo().getExt3())) {
					postage_amount=postage_amount.add(new BigDecimal(goods.getGoodsInfo().getExt3()));
				}
				
				//计算商品总价,加单品总价
				total_amount=total_amount.add(details_amount);
				try {
					orderDetailsService.insertSelective(orderDetails);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			//计算商品总价,加邮费总额
			total_amount=total_amount.add(postage_amount);
			order.setDiscount_amount(discount_amount);
			order.setTotal_amount(total_amount);
			order.setPostage_amount(postage_amount);
			try {
				orderService.insertSelective(order);
			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.info("创建订单："+order.getOrder_number());
		}
		
		//获取支付方式
		List<PaymentMethod> payment = paymentMethodService.getPaymentMethod();
		
		model.addAttribute("payment", payment);
		model.addAttribute("order", order);
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
