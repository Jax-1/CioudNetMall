package com.mall.controller.order;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mall.controller.AbstractController;
import com.mall.entity.cms.AuthorWithBLOBs;
import com.mall.entity.goods.Goods;
import com.mall.entity.inventory.InventoryDeivery;
import com.mall.entity.inventory.InventoryDeiveryAction;
import com.mall.entity.order.Order;
import com.mall.entity.order.OrderAction;
import com.mall.entity.order.OrderDetails;
import com.mall.message.SystemCode;
import com.mall.service.cms.AuthorWithBLOBsService;
import com.mall.service.goods.GoodsService;
import com.mall.service.inventory.InventoryDeiveryActionService;
import com.mall.service.inventory.InventoryDeiveryService;
import com.mall.service.order.OrderActionService;
import com.mall.service.order.OrderService;
import com.mall.service.sys.CacheService;
import com.mall.util.PageResult;


@Controller 
@RequestMapping("/admin/order")
public class AdminOrderController  extends AbstractController{
	@Resource
	private CacheService cacheService;
	@Resource
	private OrderService orderService;
	@Resource
	private GoodsService goodsService;
	@Resource
	private AuthorWithBLOBsService authorWithBLOBsService;
	@Resource
	private OrderActionService orderActionService;
	@Resource
	private InventoryDeiveryService inventoryDeiveryService;
	@Resource
	private InventoryDeiveryActionService inventoryDeiveryActionService;
	
	@RequestMapping("/list")
	public String toOrderList(Model model ,Order order ,PageResult<Order> list) {
		//获取订单列表
		int pageSize  =  Integer.parseInt(cacheService.getCache(SystemCode.PAGE).get(SystemCode.GOODS_PAGE));
		list.setPageSize(pageSize);
		list =orderService.queryByPageFront(list,order);
		model.addAttribute("orderentity", order);
		model.addAttribute("list", list);
		model.addAttribute("page", "admin/order/order_list");
		model.addAttribute("order", "nav-item start active open");
		return "admin/index";
	}
	@RequestMapping("/detail")
	public String toOrderDetail(Model model,Order order) {
		order=orderService.selectInfo(order);
		for(OrderDetails OrderDetails :order.getOrderDetailsList()) {
			//获取商品信息
			Goods goods=new Goods();
			goods.setGoods_id(OrderDetails.getGoods_id());
			goods=goodsService.selectInfo(goods);
			//获取作家信息
			AuthorWithBLOBs auth=new AuthorWithBLOBs();
			auth.setId(goods.getGoodsInfo().getAuth_id());
			auth=authorWithBLOBsService.selectInfo(auth);
			goods.setAuth(auth);
			OrderDetails.setGoods(goods);
		}
		//获取订单操作流水
		PageResult<OrderAction> list =new PageResult<OrderAction>();
		list.setPageSize(100);
		OrderAction orderAction =new OrderAction();
		orderAction.setOrder_number(order.getOrder_number());
		list=orderActionService.queryByPageFront(list,orderAction);
		//获取发货单信息
		InventoryDeivery inventoryDeivery = new InventoryDeivery();
		inventoryDeivery.setOrder_number(order.getOrder_number());
		inventoryDeivery=inventoryDeiveryService.selectByNumber(inventoryDeivery);
		model.addAttribute("deivery", inventoryDeivery);
		model.addAttribute("list", list.getDataList());
		model.addAttribute("entity", order);
		model.addAttribute("page", "admin/order/order_detail");
		model.addAttribute("order", "nav-item start active open");
		return "admin/index";
	}
	
	@RequestMapping("/deivery/list")
	public String toDeiveryList(InventoryDeivery inventoryDeivery,Model model,PageResult<InventoryDeivery> list) {
		int pageSize  =  Integer.parseInt(cacheService.getCache(SystemCode.PAGE).get(SystemCode.GOODS_PAGE));
		list.setPageSize(pageSize);
		list=inventoryDeiveryService.queryByPageFront(list, inventoryDeivery);
		for(InventoryDeivery InventoryDeivery:list.getDataList() ) {
			Order order = orderService.selectByPrimaryKey(InventoryDeivery.getOrder_number());
			InventoryDeivery.setOrder(order);
		}
		model.addAttribute("list", list);
		model.addAttribute("page", "admin/order/deliver_list");
		model.addAttribute("order", "nav-item start active open");
		return "admin/index";
		
	}
	
	@RequestMapping("/deivery/detail")
	public String toDeiveryDetail(InventoryDeivery inventoryDeivery,Model model) {
		//获取发货单信息
		inventoryDeivery=inventoryDeiveryService.selectByPrimaryKey(inventoryDeivery.getDelivery_number());
		//获取订单信息
		Order order =new Order();
		order.setOrder_number(inventoryDeivery.getOrder_number());
		order=orderService.selectInfo(order);
		for(OrderDetails OrderDetails :order.getOrderDetailsList()) {
			//获取商品信息
			Goods goods=new Goods();
			goods.setGoods_id(OrderDetails.getGoods_id());
			goods=goodsService.selectInfo(goods);
			//获取作家信息
			AuthorWithBLOBs auth=new AuthorWithBLOBs();
			auth.setId(goods.getGoodsInfo().getAuth_id());
			auth=authorWithBLOBsService.selectInfo(auth);
			goods.setAuth(auth);
			OrderDetails.setGoods(goods);
		}
		//获取发货单操作流水
		PageResult<InventoryDeiveryAction> list =new PageResult<InventoryDeiveryAction>();
		list.setPageSize(100);
		InventoryDeiveryAction inventoryDeiveryAction =new InventoryDeiveryAction();
		inventoryDeiveryAction.setDelivery_number(inventoryDeivery.getDelivery_number());
		list=inventoryDeiveryActionService.queryByPageFront(list, inventoryDeiveryAction);
		
		model.addAttribute("list", list.getDataList());
		model.addAttribute("deivery", inventoryDeivery);
		model.addAttribute("entity", order);
		model.addAttribute("page", "admin/order/deliver_detail");
		model.addAttribute("order", "nav-item start active open");
		return "admin/index";
		
	}

}
