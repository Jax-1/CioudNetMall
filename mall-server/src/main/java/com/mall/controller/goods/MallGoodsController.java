package com.mall.controller.goods;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mall.controller.AbstractController;
import com.mall.entity.ad.Ad;
import com.mall.entity.cms.AuthorWithBLOBs;
import com.mall.entity.goods.Goods;
import com.mall.entity.goods.GoodsCategory;
import com.mall.entity.goods.GoodsHistory;
import com.mall.entity.goods.GoodsPrice;
import com.mall.entity.user.User;
import com.mall.message.SystemCode;
import com.mall.service.ad.AdService;
import com.mall.service.cms.AuthorWithBLOBsService;
import com.mall.service.goods.GoodsCategoryService;
import com.mall.service.goods.GoodsService;
import com.mall.service.goods.GoodsHistoryService;
import com.mall.service.sys.CacheService;
import com.mall.util.DateFormatUtil;
import com.mall.util.PageResult;
import com.mall.util.SessionUtil;
import com.mall.util.Validate;

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
		//推荐作家
		List<AuthorWithBLOBs> auth = authorWithBLOBsService.queryRecommendAtt(new AuthorWithBLOBs());
		logger.info("获取推荐作家："+auth.size());
		//文件服务器路径
		Map<String, String> cache = cacheService.getCache(SystemCode.FILE_SERVICE);
		String url=cache.get(SystemCode.FILE_SERVICE_URL);
		String port=cache.get(SystemCode.FILE_SERVICE_PORT);
		String filePath=cache.get(SystemCode.FILE_SERVICE_FILES_PATH);
		String fileUrlPrefix=url+":"+port+"/"+filePath;
		//获取推荐商品
		Goods goods=new Goods();
		goods.setRecommend("Y");//推荐
		goods.setIs_marketable("Y");//上架
		PageResult<Goods> list =new PageResult<Goods>();
		int pageSize  =  Integer.parseInt(cacheService.getCache(SystemCode.PAGE).get(SystemCode.MALL_GOODS_REC_PAGE));
		list.setPageSize(pageSize);
		PageResult<Goods> RecGoods = goodsService.queryByPageFront(list, goods);
		logger.info("获取推荐商品："+RecGoods.getDataList().size());
		
		model.addAttribute("RecGoods", RecGoods.getDataList());
		//热卖商品
		Goods SEgoods=new Goods();
		SEgoods.setSalesSort("DESC");
		SEgoods.setIs_marketable("Y");
		int pageSizeShuf  =  Integer.parseInt(cacheService.getCache(SystemCode.PAGE).get(SystemCode.MALL_GOODS_SHUF_PAGE));
		list.setPageSize(pageSizeShuf);
		PageResult<Goods> SEGoods = goodsService.queryByPageFront(list, SEgoods);
		model.addAttribute("SEGoods", SEGoods.getDataList());
		//新品
		Goods newgoods=new Goods();
		newgoods.setNew_product("Y");
		newgoods.setIs_marketable("Y");
		PageResult<Goods> newGoods = goodsService.queryByPageFront(list, newgoods);
		model.addAttribute("newGoods", newGoods.getDataList());
		
		//人气
		Goods popgoods=new Goods();
		popgoods.setPopularitySort("DESC");
		popgoods.setIs_marketable("Y");
		PageResult<Goods> POPGoods=goodsService.queryByPageFront(list, popgoods);
		model.addAttribute("POPGoods", POPGoods.getDataList());
		//精品
		Goods clagoods=new Goods();
		clagoods.setClassic("Y");
		clagoods.setIs_marketable("Y");
		PageResult<Goods> classicGoods = goodsService.queryByPageFront(list, clagoods);
		model.addAttribute("classicGoods", classicGoods.getDataList());
		
		//特惠
		GoodsPrice goodsPrice =new GoodsPrice();
		goodsPrice.setSale("Y");
		Goods goodsSale=new Goods();
		goodsSale.setGoodsPrice(goodsPrice);
		goodsSale.setIs_marketable("Y");
		PageResult<Goods> saleGoods = goodsService.queryByPageFront(list, goodsSale);
		model.addAttribute("saleGoods", saleGoods.getDataList());
		
		model.addAttribute("adList", adList.getDataList());
		
		
//		model.addAttribute("newGoods", newGoods.getDataList());
//		model.addAttribute("classicGoods", classicGoods.getDataList());
//		model.addAttribute("saleGoods", saleGoods.getDataList());
//		model.addAttribute("POPGoods", POPGoods.getDataList());
		
		model.addAttribute("fileServicePath", fileUrlPrefix);
		
		model.addAttribute("auth", auth);
		model.addAttribute("goodsCategoryList", goodsCategoryList);
		model.addAttribute("page", "mall/goods/goods_index");
		return "mall/index";
	}
	@RequestMapping("/detail")
	public String toGoodsDetail(Model model,Goods goods,HttpServletRequest request) {
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
		//文件服务器路径
		Map<String, String> cache = cacheService.getCache(SystemCode.FILE_SERVICE);
		String url=cache.get(SystemCode.FILE_SERVICE_URL);
		String port=cache.get(SystemCode.FILE_SERVICE_PORT);
		String filePath=cache.get(SystemCode.FILE_SERVICE_FILES_PATH);
		String fileUrlPrefix=url+":"+port+"/"+filePath;
		model.addAttribute("fileServicePath", fileUrlPrefix);
		
		model.addAttribute("goods", goods);
		model.addAttribute("page", "mall/goods/goods_show");
		return "mall/index";
	}
	/**
	 * 商品列表
	 * @param model
	 * @param goods
	 * @return
	 */
	@RequestMapping("/list")
	public String toGoodsList(Model model,Goods goods,PageResult<Goods> list) {
		/**
		 * 上架商品过滤
		 */
		goods.setIs_marketable("Y");
		int pageSize  =  Integer.parseInt(cacheService.getCache(SystemCode.PAGE).get(SystemCode.GOODS_PAGE));
		list.setPageSize(pageSize);
		logger.info("log:"+goods.getPopularitySort()+"--"+goods.getSalesSort());
		list = goodsService.queryByPageFront(list, goods);
		//查询所有分类
		List<GoodsCategory> goodsCategoryList = goodsCategoryService.getGoodsCategoryList(null);
		logger.info("获取商品分类列表："+goodsCategoryList.size());
		//文件服务器路径
		Map<String, String> cache = cacheService.getCache(SystemCode.FILE_SERVICE);
		String url=cache.get(SystemCode.FILE_SERVICE_URL);
		String port=cache.get(SystemCode.FILE_SERVICE_PORT);
		String filePath=cache.get(SystemCode.FILE_SERVICE_FILES_PATH);
		String fileUrlPrefix=url+":"+port+"/"+filePath;
		model.addAttribute("fileServicePath", fileUrlPrefix);
		model.addAttribute("goodsCategoryList", goodsCategoryList);
		model.addAttribute("goods", goods);
		model.addAttribute("list", list);
		model.addAttribute("page", "mall/goods/goods_list");
		return "mall/index";
	}
}
