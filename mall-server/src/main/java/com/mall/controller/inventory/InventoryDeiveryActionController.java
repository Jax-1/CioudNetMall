package com.mall.controller.inventory;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.controller.AbstractController;
import com.mall.entity.inventory.InventoryDeivery;
import com.mall.entity.inventory.InventoryDeiveryAction;
import com.mall.entity.login.Admin;
import com.mall.message.ProcessResult;
import com.mall.service.inventory.InventoryDeiveryActionService;
import com.mall.service.inventory.InventoryDeiveryService;
import com.mall.util.DateFormatUtil;
import com.mall.util.SessionUtil;


/**
 * 发货单操作记录
 * @author 34800
 *
 */
@Controller
@RequestMapping("/admin/InventoryDeivery/action")
public class InventoryDeiveryActionController extends AbstractController{
	@Resource
	private InventoryDeiveryActionService inventoryDeiveryActionService;
	@Resource
	private InventoryDeiveryService inventoryDeiveryService;
	
	@RequestMapping("/add")
	@ResponseBody
	public ProcessResult<InventoryDeiveryAction> processDeiveryAction(InventoryDeiveryAction inventoryDeiveryAction,HttpServletRequest request){
		logger.info("创建发货单操作记录！发货单号："+inventoryDeiveryAction.getDelivery_number());
		ProcessResult<InventoryDeiveryAction> res= new ProcessResult<InventoryDeiveryAction>();
		InventoryDeivery inventoryDeivery=new InventoryDeivery();
		inventoryDeivery.setDelivery_number(inventoryDeiveryAction.getDelivery_number());
		inventoryDeivery = inventoryDeiveryService.selectByPrimaryKey(inventoryDeivery.getDelivery_number());
		
		Admin adminUser = SessionUtil.getAdminUser(request);
		inventoryDeiveryAction.setAction_user(adminUser.getAdmin_name());
		inventoryDeiveryAction.setAction_status(inventoryDeivery.getStatus());
		inventoryDeiveryAction.setDelivery_id(inventoryDeivery.getDelivery_id());
		inventoryDeiveryAction.setDelivery_status(inventoryDeivery.getLogistics_state());
		inventoryDeiveryAction.setLog_time(DateFormatUtil.getDate());
		try {
			inventoryDeiveryActionService.insertSelective(inventoryDeiveryAction);
			res=ProcessResult.success(res);
			res.setObj(inventoryDeiveryAction);
			return res;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
		
	}

}
