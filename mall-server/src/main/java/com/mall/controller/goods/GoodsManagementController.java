package com.mall.controller.goods;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mall.controller.AbstractController;
import com.mall.entity.goods.Goods;
import com.mall.entity.goods.GoodsCategory;
import com.mall.service.goods.GoodsCategoryService;
import com.mall.service.goods.GoodsInfoService;
import com.mall.service.goods.GoodsPriceService;
import com.mall.service.goods.GoodsService;
import com.mall.service.inventory.InventoryService;

@Controller
@RequestMapping("/admin/goods")
public class GoodsManagementController extends AbstractController{
	@Resource
	private GoodsCategoryService goodsCategoryService;
	@Resource
	private GoodsService goodsService;
	@Resource
	private GoodsInfoService GoodsInfoService;
	@Resource
	private GoodsPriceService GoodsPriceService;
	@Resource
	private InventoryService InventoryService;
	
	@GetMapping("/category")
	public String toClassify(Model model) {
		//查询所有分类
		List<GoodsCategory> goodsCategoryList = goodsCategoryService.getGoodsCategoryList(null);
		logger.info("获取商品分类列表："+goodsCategoryList.size());				model.addAttribute("goodsCategoryList", goodsCategoryList);
		model.addAttribute("page", "admin/goods/classify_goods");
		model.addAttribute("mall", "nav-item start active open");
		return "admin/index";
		
	}
	@RequestMapping("/list")
	public String toGoodsList(Model model) {
		
		model.addAttribute("page", "admin/goods/list_goods");
		model.addAttribute("mall", "nav-item start active open");
		return "admin/index";
		
	}
	@RequestMapping("/add")
	public String toGoodsAdd(Model model) {
		model.addAttribute("page", "admin/goods/add_goods");
		model.addAttribute("mall", "nav-item start active open");
		return "admin/index";
		
	}
	
	@RequestMapping("/save")
	public String toGoodsSave(Model model,Goods goods,HttpServletRequest request,String editorValue) {
		goods.init(goods, request, editorValue);
		logger.info("保存商品信息："+goods.getGoods_name());
		try {
			goodsService.insertSelective(goods);
			GoodsInfoService.insertSelective(goods.getGoodsInfo());
			GoodsPriceService.insertSelective(goods.getGoodsPrice());
			InventoryService.insertSelective(goods.getGoodsInfo().getInventory());
		} catch (Exception e) {
			logger.error("保存商品信息：失败"+e.getMessage());
			e.printStackTrace();
		}
		
		model.addAttribute("page", "admin/goods/list_goods");
		model.addAttribute("mall", "nav-item start active open");
		return "admin/index";
		
	}
	

	
}
