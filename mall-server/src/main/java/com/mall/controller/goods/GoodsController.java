package com.mall.controller.goods;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.controller.AbstractController;
import com.mall.entity.goods.Goods;
import com.mall.message.ProcessResult;
import com.mall.service.goods.GoodsService;
import com.mall.service.goods.GoodsInfoService;
import com.mall.service.goods.GoodsPriceService;
import com.mall.service.inventory.InventoryService;


@Controller
@RequestMapping("/admin/goods")
public class GoodsController extends AbstractController{
	@Resource
	private GoodsService goodsService;
	@Resource
	private GoodsInfoService GoodsInfoService;
	@Resource
	private GoodsPriceService GoodsPriceService;
	@Resource
	private InventoryService InventoryService;
	
	@PostMapping("/de")
	@ResponseBody
	public ProcessResult<Goods> getTest(Goods goods,HttpServletRequest request,String editorValue){
		
		ProcessResult<Goods> res=new ProcessResult<Goods>();
		try {
			goods=goods.init(goods, request, editorValue);
			goodsService.insertSelective(goods);
			GoodsInfoService.insertSelective(goods.getGoodsInfo());
			GoodsPriceService.insertSelective(goods.getGoodsPrice());
			InventoryService.insertSelective(goods.getGoodsInfo().getInventory());
			res=ProcessResult.success(res);
		} catch (Exception e) {
			logger.error("保存商品分类信息：失败！"+e.getMessage());
			e.printStackTrace();
		}
		return res;
	}
}
