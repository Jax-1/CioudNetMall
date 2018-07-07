package com.mall.controller.goods;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mall.controller.AbstractController;
import com.mall.entity.cms.AuthorWithBLOBs;
import com.mall.entity.goods.GoodsCategory;
import com.mall.message.SystemCode;
import com.mall.service.cms.AuthorWithBLOBsService;
import com.mall.service.goods.GoodsCategoryService;
import com.mall.service.goods.GoodsService;
import com.mall.service.sys.CacheService;

@Controller
@RequestMapping("/mall/goods")
public class MallGoodsController extends AbstractController{
	@Resource
	private GoodsService goodsService;
	@Resource
	private CacheService cacheService;
	@Resource
	private GoodsCategoryService goodsCategoryService;
	@Resource
	private AuthorWithBLOBsService authorWithBLOBsService;
	@RequestMapping("")
	public String toGoodsList(Model model) {
		//查询所有分类
		List<GoodsCategory> goodsCategoryList = goodsCategoryService.getGoodsCategoryList(null);
		logger.info("获取商品分类列表："+goodsCategoryList.size());
		//推荐作家
		List<AuthorWithBLOBs> auth = authorWithBLOBsService.queryRecommendAtt(new AuthorWithBLOBs());
		model.addAttribute("auth", auth);
		model.addAttribute("goodsCategoryList", goodsCategoryList);
		model.addAttribute("page", "mall/goods/goods_index");
		return "mall/index";
	}
	@RequestMapping("detail")
	public String toGoodsDetail(Model model) {
		model.addAttribute("page", "mall/goods/goods_show");
		return "mall/index";
	}
}
