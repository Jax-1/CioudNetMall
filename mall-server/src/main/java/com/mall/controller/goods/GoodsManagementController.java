package com.mall.controller.goods;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mall.controller.AbstractController;
import com.mall.entity.cms.AuthorWithBLOBs;
import com.mall.entity.goods.Goods;
import com.mall.entity.goods.GoodsCategory;
import com.mall.message.SystemCode;
import com.mall.service.cms.AuthorWithBLOBsService;
import com.mall.service.goods.GoodsCategoryService;
import com.mall.service.goods.GoodsInfoService;
import com.mall.service.goods.GoodsPriceService;
import com.mall.service.goods.GoodsService;
import com.mall.service.inventory.InventoryService;
import com.mall.service.sys.CacheService;
import com.mall.util.PageResult;
import com.mall.util.Validate;

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
	@Resource
	private AuthorWithBLOBsService authorWithBLOBsService;
	@Resource
	private CacheService cacheService;
	@GetMapping("/category")
	public String toClassify(Model model) {
		//查询所有分类
		List<GoodsCategory> goodsCategoryList = goodsCategoryService.getGoodsCategoryList(null);
		logger.info("获取商品分类列表："+goodsCategoryList.size());				
		model.addAttribute("goodsCategoryList", goodsCategoryList);
		model.addAttribute("page", "admin/goods/classify_goods");
		model.addAttribute("mall", "nav-item start active open");
		return "admin/index";
		
	}
	@RequestMapping("/list")
	public String toGoodsList(Model model,PageResult<Goods> list,Goods goods) {
		int pageSize  =  Integer.parseInt(cacheService.getCache(SystemCode.PAGE).get(SystemCode.MALL_ATT_PAGE));
		list.setPageSize(pageSize);
		list=goodsService.queryByPageFront(list, goods);
		model.addAttribute("list",list);
		model.addAttribute("page", "admin/goods/list_goods");
		model.addAttribute("mall", "nav-item start active open");
		return "admin/index";
		
	}
	@RequestMapping("/editor")
	public String toGoodsAdd(Model model,Goods goods) {
		//查询所有分类
		List<GoodsCategory> goodsCategoryList = goodsCategoryService.getGoodsCategoryList(null);
		logger.info("获取商品分类列表："+goodsCategoryList.size());
		
		//查询所有作家
		PageResult<AuthorWithBLOBs> list =new PageResult<AuthorWithBLOBs>();
		AuthorWithBLOBs auth =new AuthorWithBLOBs();
		list.setPageSize(0);
		auth.setColumns("MJHC");
		list = authorWithBLOBsService.queryByPageFront(list, auth);
		/**
		 * 编辑
		 */
		if(Validate.notNull(goods)) {
			logger.info("编辑商品信息：ID="+goods.getGoods_id());
			Goods info = goodsService.selectInfo(goods);
			model.addAttribute("info", info);
			Map<String, String> cache = cacheService.getCache(SystemCode.FILE_SERVICE);
			String url=cache.get(SystemCode.FILE_SERVICE_URL);
			String port=cache.get(SystemCode.FILE_SERVICE_PORT);
			String filePath=cache.get(SystemCode.FILE_SERVICE_FILES_PATH);
			String fileUrlPrefix=url+":"+port+"/"+filePath;
			//文件服务器路径
			model.addAttribute("fileServicePath", fileUrlPrefix);
		}
		model.addAttribute("goodsCategoryList", goodsCategoryList);
		model.addAttribute("authlist", list);
		model.addAttribute("page", "admin/goods/add_goods");
		model.addAttribute("mall", "nav-item start active open");
		return "admin/index";
		
	}
	
	@RequestMapping("/save")
	public String toGoodsSave(Model model,Goods goods,HttpServletRequest request,String editorValue,String type) {
		if(SystemCode.TYPE_SAVE.equals(type)) {
			//保存操作
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
		}else if(SystemCode.TYPE_UPDATE.equals(type)) {
			//更新操作
			try {
				goodsService.updateByPrimaryKeySelective(goods);
				GoodsInfoService.updateByPrimaryKeySelective(goods.getGoodsInfo());
				GoodsPriceService.updateByPrimaryKeySelective(goods.getGoodsPrice());
				InventoryService.updateByPrimaryKeySelective(goods.getGoodsInfo().getInventory());
			} catch (Exception e) {
				logger.error("更新商品信息：失败"+e.getMessage());
				e.printStackTrace();
			}
			
		}
		return "redirect:/admin/goods/editor";
		
	}
	

	
}
