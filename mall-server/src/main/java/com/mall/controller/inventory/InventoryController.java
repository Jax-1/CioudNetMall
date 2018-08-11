package com.mall.controller.inventory;

import java.util.Map;

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
import com.mall.service.sys.CacheService;
import com.mall.util.Validate;

@Controller
@RequestMapping("/mall/inventory")
public class InventoryController extends AbstractController{
	@Resource
	private InventoryService inventoryService;
	@Resource
	private CacheService cacheService;
	@PostMapping("/retrieve")
	@ResponseBody
	public ProcessResult<Inventory> retrieve(Inventory inventory ,Integer amount){
		ProcessResult<Inventory> res=new ProcessResult<Inventory>();
		inventory =inventoryService.inventoryRetrieve(inventory);
		if(Validate.notNull(inventory)
				&&inventory.getAmount()>0
				&&inventory.getAmount()-inventory.getStay_amount()>=amount) {
			
			Map<String, Integer> bePaidGoods = cacheService.toBePaidGoods(inventory.getProductNumber(),0);
			logger.info("获取正在支付缓存信息，商品ID："+inventory.getProductNumber()+"正支付商品数量："+bePaidGoods);
			if(bePaidGoods!=null&&(inventory.getAmount()-inventory.getStay_amount()-Integer.valueOf(bePaidGoods.get(inventory.getProductNumber()))>=amount)) {
				res=ProcessResult.success(res);
				res.setObj(inventory);
				return res;
			}else if(bePaidGoods==null) {
				//缓存数据为空，直接返回
				res=ProcessResult.success(res);
				res.setObj(inventory);
				return res;
			}
			
		}
		res.setMsg("库存不足！");
		return res;
	}
}
