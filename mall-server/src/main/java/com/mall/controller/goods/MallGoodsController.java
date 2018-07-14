package com.mall.controller.goods;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mall.controller.AbstractController;
import com.mall.entity.cms.AuthorWithBLOBs;
import com.mall.entity.goods.Goods;
import com.mall.entity.goods.GoodsCategory;
import com.mall.entity.goods.GoodsHistory;
import com.mall.entity.goods.GoodsPrice;
import com.mall.entity.login.User;
import com.mall.message.SystemCode;
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
	@RequestMapping("")
	public String toGoodsList(Model model) {
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
		goods.setRecommend("");
		//新品
		goods.setNew_product("Y");
		int pageSizeShuf  =  Integer.parseInt(cacheService.getCache(SystemCode.PAGE).get(SystemCode.MALL_GOODS_SHUF_PAGE));
		list.setPageSize(pageSizeShuf);
		PageResult<Goods> newGoods = goodsService.queryByPageFront(list, goods);
		//人气
		goods.setNew_product("");
		
		//精品
		goods.setClassic("Y");
		PageResult<Goods> classicGoods = goodsService.queryByPageFront(list, goods);
		//特惠
		GoodsPrice goodsPrice =new GoodsPrice();
		goodsPrice.setSale("Y");
		goods.setGoodsPrice(goodsPrice);
		PageResult<Goods> saleGoods = goodsService.queryByPageFront(list, goods);
		
		
		
		model.addAttribute("newGoods", newGoods);
		model.addAttribute("classicGoods", classicGoods);
		model.addAttribute("saleGoods", saleGoods);
		
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
		int pageSize  =  Integer.parseInt(cacheService.getCache(SystemCode.PAGE).get(SystemCode.GOODS_PAGE));
		list.setPageSize(pageSize);
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
		model.addAttribute("list", list);
		model.addAttribute("page", "mall/goods/goods_list");
		return "mall/index";
	}
}
