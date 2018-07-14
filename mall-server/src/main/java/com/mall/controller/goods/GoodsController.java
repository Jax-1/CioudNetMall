package com.mall.controller.goods;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.controller.AbstractController;
import com.mall.entity.cms.AuthorWithBLOBs;
import com.mall.entity.goods.Goods;
import com.mall.entity.goods.GoodsCategory;
import com.mall.entity.goods.GoodsList;
import com.mall.message.ProcessResult;
import com.mall.message.SystemCode;
import com.mall.service.goods.GoodsService;
import com.mall.service.cms.AuthorWithBLOBsService;
import com.mall.service.goods.GoodsInfoService;
import com.mall.service.goods.GoodsPriceService;
import com.mall.service.inventory.InventoryService;
import com.mall.service.sys.CacheService;
import com.mall.util.PageResult;


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
	@Resource
	private CacheService cacheService;
	
	@Resource
	private AuthorWithBLOBsService authorWithBLOBsService;
	
	
	/**
	 * 批量删除
	 * @param list
	 * @return
	 */
	@PostMapping("/batchDelete")
	@ResponseBody
	public ProcessResult<Goods> batchDelete(GoodsList list) {
		System.out.println(list.getList().size());
		ProcessResult<Goods> res=new ProcessResult<Goods>();
		try {
			int delete = goodsService.batchDelete(list.getList());
			if(delete>0) {
				res.setRes(SystemCode.SUCCESS);
				res.setMsg("批量更新完成！");
			}
		} catch (Exception e) {
			logger.error("批量删除商品信息：失败！"+e.getMessage());
		}
		
		return res;
		
	}
	/**
	 * 批量上架
	 * @param list
	 * @return
	 */
	@PostMapping("/batchMarketableUp")
	@ResponseBody
	public ProcessResult<Goods> batchMarketableUp(GoodsList list) {
		ProcessResult<Goods> res=new ProcessResult<Goods>();
		try {
			int delete = goodsService.batchMarketableUp(list.getList());
			if(delete>0) {
				res.setRes(SystemCode.SUCCESS);
				res.setMsg("批量更新完成！");
			}
		} catch (Exception e) {
			logger.error("批量上架商品信息：失败！"+e.getMessage());
		}
		
		return res;
		
	}
	/**
	 * 批量下架
	 * @param list
	 * @return
	 */
	@PostMapping("/batchMarketableDown")
	@ResponseBody
	public ProcessResult<Goods> batchMarketableDown(GoodsList list) {
		ProcessResult<Goods> res=new ProcessResult<Goods>();
		try {
			int delete = goodsService.batchMarketableDown(list.getList());
			if(delete>0) {
				res.setRes(SystemCode.SUCCESS);
				res.setMsg("批量更新完成！");
			}
		} catch (Exception e) {
			logger.error("批量下架商品信息：失败！"+e.getMessage());
		}
		
		return res;
		
	}
	/**
	 * 商品列表,测试
	 * @param model
	 * @param goods
	 * @return
	 */
	@PostMapping("/list-test")
	@ResponseBody
	public PageResult<Goods> toGoodsList(Model model,Goods goods,PageResult<Goods> list) {
		int pageSize  =  Integer.parseInt(cacheService.getCache(SystemCode.PAGE).get(SystemCode.GOODS_PAGE));
		list.setPageSize(pageSize);
		list = goodsService.queryByPageFront(list, goods);
		return list;
	}
	
}
