package com.mall.api.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.api.BaseAPI;
import com.mall.entity.cms.AuthorWithBLOBs;
import com.mall.entity.goods.Goods;
import com.mall.entity.order.Order;
import com.mall.entity.order.OrderDetails;
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

@Controller
@RequestMapping("api/order")
public class OrderApi extends BaseAPI {
	@Resource
	private OrderAddressService orderAddressService;
	@Resource
	private GoodsService goodsService;
	@Resource
	private CacheService cacheService;
	@Resource
	private AuthorWithBLOBsService authorWithBLOBsService;
	@Resource
	private OrderDetailsService orderDetailsService;
	@Resource
	private OrderService orderService;
	/**
	 * 订单页
	 * @param order
	 * @param goods
	 * @param amount
	 * @return
	 */
	@RequestMapping("")
	@ResponseBody
	public Order toOrderPayMent(Order order,Goods goods,Integer amount) {
		//单商品购买
		if(Validate.notNull(goods)&& goods.getGoods_id()!=null) {
			//获取商品信息
			OrderDetails orderDetails=new OrderDetails();
			orderDetails.setGoods_id(goods.getGoods_id());
			logger.info("单商品购买："+goods.getGoods_id());
			orderDetails.setNum(amount);
			List<OrderDetails> list =new ArrayList<OrderDetails>();
			list.add(orderDetails);
			order.setOrderDetailsList(list);
		}
//		//已有订单支付
//		if(Validate.notNull(order)&&order.getOrder_number()!=null) {
//			order=orderService.selectInfo(order);
//			for(OrderDetails g:order.getOrderDetailsList()) {
//				Goods goodsSelect =new Goods();
//				goodsSelect.setGoods_id(g.getGoods_id());
//				goodsSelect = goodsService.selectInfo(goodsSelect);
//				if(Validate.notNull(goodsSelect.getGoodsInfo().getAuth_id())) {
//					//查询商品作家信息
//					AuthorWithBLOBs a=new AuthorWithBLOBs();
//					a.setId(goodsSelect.getGoodsInfo().getAuth_id());
//					a = authorWithBLOBsService.selectInfo(a);
//					goodsSelect.setAuth(a);
//				}
//				goodsSelect.setAmount(g.getNum());
//				list.add(goodsSelect);
//			}
//		}
		order=orderService.calculationOrderAmount(order);
		return order;
		
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
	@ResponseBody
	public Order toOrderPay(Model model,Order order,HttpServletRequest request) {
		//
		if(Validate.notNull(order.getOrder_number())) {
			Order orderUpdate=order;
			order=orderService.selectInfo(order);
			//历史订单支付
			logger.info("历史订单支付！"+order.getOrder_number());
			order=orderService.calculationOrderAmount(order);
			
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
			order=orderService.calculationOrderAmount(order);
			
			logger.info("创建订单："+order.getOrder_number());
		}
		return order;
	}
	

}
