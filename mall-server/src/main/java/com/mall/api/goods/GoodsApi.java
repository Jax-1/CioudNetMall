package com.mall.api.goods;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.api.BaseAPI;
import com.mall.entity.cms.AuthorWithBLOBs;
import com.mall.entity.goods.Goods;
import com.mall.entity.goods.GoodsCategory;
import com.mall.entity.goods.GoodsHistory;
import com.mall.entity.login.User;
import com.mall.message.SystemCode;
import com.mall.service.ad.AdService;
import com.mall.service.cms.AuthorWithBLOBsService;
import com.mall.service.goods.GoodsCategoryService;
import com.mall.service.goods.GoodsHistoryService;
import com.mall.service.goods.GoodsService;
import com.mall.service.sys.CacheService;
import com.mall.util.DateFormatUtil;
import com.mall.util.PageResult;
import com.mall.util.SessionUtil;
import com.mall.util.Validate;

/**
 * 商品API
 * @author Jang
 *
 */
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

	@GetMapping("category")
	@ResponseBody
	public List<GoodsCategory> getGoodsCategory(GoodsCategory goodsCategory) {
		
		//查询所有分类
		List<GoodsCategory> goodsCategoryList = goodsCategoryService.getGoodsCategoryOneList(goodsCategory);
		logger.info("获取商品分类列表："+goodsCategoryList.size());
		
		return goodsCategoryList;
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
	
	/**
	 * 商品详情
	 * @param goods
	 * @param request
	 * @return
	 */
	@GetMapping("detail")
	@ResponseBody
	public Goods getGoodsDetail(Goods goods,HttpServletRequest request) {
		goods = goodsService.selectInfo(goods);
		if(Validate.notNull(goods.getGoodsInfo().getAuth_id())) {
			//查询商品作家信息
			AuthorWithBLOBs a=new AuthorWithBLOBs();
			a.setId(goods.getGoodsInfo().getAuth_id());
			a = authorWithBLOBsService.selectInfo(a);
			goods.setAuth(a);
		}
		User user = SessionUtil.getUser(request);
		GoodsHistory goodsHistory =new GoodsHistory();
		goodsHistory.setCreate_time(DateFormatUtil.getDate());
		goodsHistory.setGood_id(goods.getGoods_id());
		if(Validate.notNull(user)) {
			//用户登录了
			goodsHistory.setUser_id(user.getUser_name());
		}else {
			//未登录，存储IP信息
			logger.info("IP:"+request.getRemoteAddr());
			goodsHistory.setUser_id(SessionUtil.getIpAddr(request));
		}
		try {
			GoodsHistoryService.insertSelective(goodsHistory);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return goods;
	}
	
	/**
	 * 商品列表
	 * @param model
	 * @param goods
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public PageResult<Goods> toGoodsList(Model model,Goods goods,PageResult<Goods> list) {
		/**
		 * 上架商品过滤
		 */
		goods.setIs_marketable("Y");
		int pageSize  =  Integer.parseInt(cacheService.getCache(SystemCode.PAGE).get(SystemCode.GOODS_PAGE));
		list.setPageSize(pageSize);
		logger.info("log:"+goods.getPopularitySort()+"--"+goods.getSalesSort());
		list = goodsService.queryByPageFront(list, goods);
		return list;
	}
}
