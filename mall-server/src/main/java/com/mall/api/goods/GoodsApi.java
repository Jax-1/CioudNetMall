package com.mall.api.goods;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.api.BaseAPI;
import com.mall.entity.ad.Ad;
import com.mall.entity.cms.AuthorWithBLOBs;
import com.mall.entity.goods.Goods;
import com.mall.entity.goods.GoodsCategory;
import com.mall.entity.goods.GoodsPrice;
import com.mall.message.SystemCode;
import com.mall.service.ad.AdService;
import com.mall.service.cms.AuthorWithBLOBsService;
import com.mall.service.goods.GoodsCategoryService;
import com.mall.service.goods.GoodsHistoryService;
import com.mall.service.goods.GoodsService;
import com.mall.service.sys.CacheService;
import com.mall.util.PageResult;

@Controller
@RequestMapping("api/goods")
public class GoodsApi extends BaseAPI{
	@Resource
	private GoodsService goodsService;
	@Resource
	private CacheService cacheService;
	@Resource
	private GoodsCategoryService goodsCategoryService;
	@Resource
	private AuthorWithBLOBsService authorWithBLOBsService;
	@Resource
	private GoodsHistoryService GoodsHistoryService;
	@Resource
	private AdService adService;
	
	@RequestMapping("")
	public String toGoodsList(Model model) {
		//获取广告信息
		PageResult<Ad> adList=new PageResult<Ad>();
		Ad ad =new Ad();
		Byte state=1;
		ad.setState(state);
		adList=adService.queryByPageFront(adList,ad);
		//查询所有分类
		List<GoodsCategory> goodsCategoryList = goodsCategoryService.getGoodsCategoryList(null);
		logger.info("获取商品分类列表："+goodsCategoryList.size());
		
		
		model.addAttribute("adList", adList.getDataList());
		
		
		model.addAttribute("goodsCategoryList", goodsCategoryList);
		model.addAttribute("page", "mall/goods/goods_index");
		return "mall/index";
	}
	/**
	 * 特惠商品
	 * GoodsPrice goodsPrice =new GoodsPrice();
	 * goodsPrice.setSale("Y");
	 * goods.setGoodsPrice(goodsPrice);
	 * goodsSale.setIs_marketable("Y");
	 * 精品
	 * Goods clagoods=new Goods();
	 * clagoods.setClassic("Y");
	 * clagoods.setIs_marketable("Y");
	 * 人气
	 * Goods popgoods=new Goods();
	 * popgoods.setPopularitySort("DESC");
	 * popgoods.setIs_marketable("Y");
	 * 新品
	 * Goods newgoods=new Goods();
	 * newgoods.setNew_product("Y");
	 * newgoods.setIs_marketable("Y");
	 * 热卖
	 * Goods SEgoods=new Goods();
	 * SEgoods.setSalesSort("DESC");
	 * SEgoods.setIs_marketable("Y");
	 * 获取推荐商品
	 * Goods goods=new Goods();
	 * goods.setRecommend("Y");//推荐
	 * goods.setIs_marketable("Y");//上架
	 * @return
	 */
	@GetMapping("/features")
	@ResponseBody
	public List<Goods> getSaleGoods(Goods goods) {
		PageResult<Goods> list =new PageResult<Goods>();
		int pageSize  =  Integer.parseInt(cacheService.getCache(SystemCode.PAGE).get(SystemCode.MALL_GOODS_SHUF_PAGE));
		list.setPageSize(pageSize);
		PageResult<Goods> saleGoods = goodsService.queryByPageFront(list, goods);
		return saleGoods.getDataList();
	}

}
