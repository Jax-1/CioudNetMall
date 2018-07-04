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
import com.mall.service.goods.GoodsService;

@Controller
@RequestMapping("/admin/goods")
public class GoodsManagementController extends AbstractController{
	@Resource
	private GoodsCategoryService goodsCategoryService;
	@Resource
	private GoodsService goodsService;
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
		goodsService.insertGoods(goods);
		model.addAttribute("page", "admin/goods/list_goods");
		model.addAttribute("mall", "nav-item start active open");
		return "admin/index";
		
	}
	

	
}
