package com.mall.controller.order;

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
import com.mall.service.payment.PaymentMethodService;
import com.mall.service.sys.CacheService;
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
	/**
	 * 订单界面
	 * @param model
	 * @param goods
	 * @param request
	 * @param amount
	 * @return
	 */
	@RequestMapping("")
	public String toOrder(Model model,Goods goods,HttpServletRequest request ,Integer amount) {
		User user = SessionUtil.getUser(request);
		//获取当前用户的收获地址
		List<OrderAddress> address = orderAddressService.userTakeDeliveryAddress(user);
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
		
		model.addAttribute("fileServicePath", fileUrlPrefix);
		model.addAttribute("payment", payment);
		model.addAttribute("amount", amount);
		model.addAttribute("goods", goods);
		model.addAttribute("address", address);
		model.addAttribute("page", "mall/order/order");
		return "mall/index";
		
	}
	@RequestMapping("/pay")
	public String toOrderPay(Model model,Order order,OrderDetails orderDetails) {
		//
		model.addAttribute("page", "mall/order/order_pay");
		return "mall/index";
		
	}
	@RequestMapping("/real")
	public String toOrderReal(Model model) {
		model.addAttribute("page", "mall/order/order_real");
		return "mall/index";
		
	}

}
