package com.mall.controller.inventory;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.controller.AbstractController;
import com.mall.dao.sequence.Sequence;
import com.mall.dao.sequence.SequenceMapper;
import com.mall.entity.inventory.InventoryDeivery;
import com.mall.entity.login.Admin;
import com.mall.entity.order.Order;
import com.mall.message.ProcessResult;
import com.mall.message.SystemCode;
import com.mall.service.inventory.InventoryDeiveryService;
import com.mall.service.order.OrderService;
import com.mall.util.DateFormatUtil;
import com.mall.util.ProcessOrderUtil;
import com.mall.util.SessionUtil;
import com.mall.util.Validate;

/**
 * 发货单控制器
 * @author Jang
 *
 */
@Controller
@RequestMapping("/admin/InventoryDeivery")
public class InventoryDeiveryController extends AbstractController{
	@Resource
	private OrderService orderService;
	@Resource
	private InventoryDeiveryService inventoryDeiveryService;
	@Resource
	private SequenceMapper sequenceMapper;
	
	
	/**
	 * 生成发货单
	 * @param inventoryDeivery
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public ProcessResult<InventoryDeivery> processInventoryDeivery(InventoryDeivery inventoryDeivery,HttpServletRequest request){
		
		ProcessResult<InventoryDeivery> res=new ProcessResult<InventoryDeivery>();
		sequenceMapper.nextval(SystemCode.INVENTORYDEIVERY_SEQUEANCE);
		Sequence sequence = sequenceMapper.currval(SystemCode.INVENTORYDEIVERY_SEQUEANCE);
		String processDeliveryNumber="";
		while (true) {
			//获取发货单号
			processDeliveryNumber = ProcessOrderUtil.processDeliveryNumber(sequence);
			InventoryDeivery deivery = inventoryDeiveryService.selectByPrimaryKey(processDeliveryNumber);
			if(!Validate.notNull(deivery)) {
				//无重复发货单号
				break;
			}
		}
		logger.info("创建发货单，单号："+processDeliveryNumber);
		inventoryDeivery.setDelivery_number(processDeliveryNumber);
		try {
			Order order=new Order();
			order.setOrder_number(inventoryDeivery.getOrder_number());
			order=orderService.selectInfo(order);
			
			//赋值
			Admin adminUser = SessionUtil.getAdminUser(request);
			inventoryDeivery.setOrder_id(order.getOrder_id());
			inventoryDeivery.setReceive_id(order.getReceive_id());
			//快递名
			inventoryDeivery.setShipping_name("");
			
			inventoryDeivery.setUser_id(order.getUser_id());
			inventoryDeivery.setAction_user(adminUser.getAdmin_name());
			inventoryDeiveryService.insertSelective(inventoryDeivery);
			
			res=ProcessResult.success(res);
			res.setObj(inventoryDeivery);
			return res;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
		
	}
	@RequestMapping("/chengeStatus")
	@ResponseBody
	public ProcessResult<InventoryDeivery> chengeStatus(InventoryDeivery inventoryDeivery){
		logger.info("修改发货单状态！发货单号："+inventoryDeivery.getDelivery_number());
		ProcessResult<InventoryDeivery> res=new ProcessResult<InventoryDeivery>();
		int updateDeiveryStatus = inventoryDeiveryService.updateDeiveryStatus(inventoryDeivery);
		if(updateDeiveryStatus>0) {
			res=ProcessResult.success(res);
			return res;
		}
		return res;
		
	}
}
