package com.mall.controller.order;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mall.controller.AbstractController;
import com.mall.entity.cms.AuthorWithBLOBs;
import com.mall.entity.goods.Goods;
import com.mall.entity.inventory.Courier;
import com.mall.entity.inventory.InventoryDeivery;
import com.mall.entity.inventory.InventoryDeiveryAction;
import com.mall.entity.order.Order;
import com.mall.entity.order.OrderAction;
import com.mall.entity.order.OrderDetails;
import com.mall.entity.order.OrderServe;
import com.mall.message.SystemCode;
import com.mall.service.cms.AuthorWithBLOBsService;
import com.mall.service.goods.GoodsService;
import com.mall.service.inventory.CourierService;
import com.mall.service.inventory.InventoryDeiveryActionService;
import com.mall.service.inventory.InventoryDeiveryService;
import com.mall.service.order.OrderActionService;
import com.mall.service.order.OrderServeService;
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
	@Resource
	private OrderServeService orderServeService;
	@Resource
	private CourierService courierService;
	
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
		//获取全部快递信息
		List<Courier> courierList = courierService.queryAll(null);
		
		model.addAttribute("courierList", courierList);
		model.addAttribute("list", list.getDataList());
		model.addAttribute("deivery", inventoryDeivery);
		model.addAttribute("entity", order);
		model.addAttribute("page", "admin/order/deliver_detail");
		model.addAttribute("order", "nav-item start active open");
		return "admin/index";
		
	}
	
	@RequestMapping("/service/list")
	public String toServerList(Model model,OrderServe orderServe,PageResult<OrderServe> list) {
		logger.info("获取用户服务单列表！");
		list=orderServeService.queryByPageFront(list, orderServe);
		model.addAttribute("list", list);
		model.addAttribute("orderServe", orderServe);
		model.addAttribute("page", "admin/order/sever_list");
		model.addAttribute("order", "nav-item start active open");
		return "admin/index";
	}
	@RequestMapping("/service/detail")
	public String toServerDetail(Model model,OrderServe orderServe) {
		logger.info("获取用户服务单详情！orderServe="+orderServe.getService_number());
		//获取售后单信息
		orderServe=orderServeService.selectInfo(orderServe);
		
		Order order =new Order();
		order.setOrder_number(orderServe.getOrder_number());
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
		
		//文件服务器路径
		Map<String, String> cache = cacheService.getCache(SystemCode.FILE_SERVICE);
		String url=cache.get(SystemCode.FILE_SERVICE_URL);
		String port=cache.get(SystemCode.FILE_SERVICE_PORT);
		String filePath=cache.get(SystemCode.FILE_SERVICE_FILES_PATH);
		String fileUrlPrefix=url+":"+port+"/"+filePath;
		model.addAttribute("fileServicePath", fileUrlPrefix);
		model.addAttribute("deivery", inventoryDeivery);
		model.addAttribute("list", list.getDataList());
		model.addAttribute("entity", order);
		model.addAttribute("orderServe", orderServe);
		model.addAttribute("page", "admin/order/server_detail");
		model.addAttribute("order", "nav-item start active open");
		return "admin/index";
	}
	

}
