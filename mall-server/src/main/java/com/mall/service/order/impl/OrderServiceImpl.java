package com.mall.service.order.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mall.dao.base.IBaseDao;
import com.mall.dao.order.OrderMapper;
import com.mall.entity.cms.AuthorWithBLOBs;
import com.mall.entity.goods.Goods;
import com.mall.entity.order.Order;
import com.mall.entity.order.OrderDetails;
import com.mall.service.BaseServiceImpl;
import com.mall.service.cms.AuthorWithBLOBsService;
import com.mall.service.goods.GoodsService;
import com.mall.service.order.OrderDetailsService;
import com.mall.service.order.OrderService;
import com.mall.util.PageResult;
import com.mall.util.Validate;
@Service
public class OrderServiceImpl extends BaseServiceImpl<Order> implements OrderService {
	@Resource
	private OrderMapper orderMapper;
	@Resource
	private GoodsService goodsService;
	@Resource
	private OrderService orderService;
	@Resource
	private OrderDetailsService orderDetailsService;
	@Resource
	private AuthorWithBLOBsService authorWithBLOBsService;
	
	@Override
	protected IBaseDao<Order> getMapper() {
		return orderMapper;
	}
	@Override
	public int updateOrderStatus(Order order) {
		return orderMapper.updateOrderStatus(order);
	}
	@Override
	public Order calculationOrderAmount(Order order) {
		BigDecimal total_amount=BigDecimal.ZERO;
		BigDecimal postage_amount=BigDecimal.ZERO;
		BigDecimal discount_amount=BigDecimal.ZERO;
		if(Validate.notNull(order)&&Validate.notNull(order.getOrderDetailsList())) {
			//已有订单计算
			Order orderUpdate=order;
			logger.info("payment_id:"+order.getPayment_id());
			//更新订单单品商品信息
			for(OrderDetails orderDetails:order.getOrderDetailsList()) {
				BigDecimal details_amount=BigDecimal.ZERO;
				BigDecimal num = new BigDecimal(orderDetails.getNum()); 
				Goods goods=new Goods();
				goods.setGoods_id(orderDetails.getGoods_id());
				logger.info("获取商品信息："+goods.getGoods_id());
				goods = goodsService.selectInfo(goods);
				if(Validate.notNull(goods.getGoodsInfo().getAuth_id())) {
					//查询商品作家信息
					AuthorWithBLOBs a=new AuthorWithBLOBs();
					a.setId(goods.getGoodsInfo().getAuth_id());
					a = authorWithBLOBsService.selectInfo(a);
					goods.setAuth(a);
				}
				orderDetails.setGoods(goods);
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
			
			
			
		}
		return order;
	}

	
}
