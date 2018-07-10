package com.mall.controller.inventory;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.controller.AbstractController;
import com.mall.entity.inventory.Inventory;
import com.mall.message.ProcessResult;
import com.mall.message.SystemCode;
import com.mall.service.inventory.InventoryService;
import com.mall.util.Validate;

@Controller
@RequestMapping("/mall/inventory")
public class InventoryController extends AbstractController{
	@Resource
	private InventoryService inventoryService;
	@PostMapping("/retrieve")
	@ResponseBody
	public ProcessResult<Inventory> retrieve(Inventory inventory ,Integer amount){
		ProcessResult<Inventory> res=new ProcessResult<Inventory>();
		inventory =inventoryService.inventoryRetrieve(inventory);
		if(Validate.notNull(inventory)&&inventory.getAmount()>0&&inventory.getAmount()>=amount) {
			
			res=ProcessResult.success(res);
			res.setObj(inventory);
			return res;
		}
		res.setMsg("库存不足！");
		return res;
	}
}
