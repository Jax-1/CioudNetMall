package com.mall.controller.goods;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.controller.AbstractController;
import com.mall.entity.cms.AuthorWithBLOBs;
import com.mall.entity.goods.Goods;
import com.mall.message.ProcessResult;
import com.mall.message.SystemCode;
import com.mall.service.goods.GoodsService;
import com.mall.service.cms.AuthorWithBLOBsService;
import com.mall.service.goods.GoodsInfoService;
import com.mall.service.goods.GoodsPriceService;
import com.mall.service.inventory.InventoryService;
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
	private AuthorWithBLOBsService authorWithBLOBsService;
	
	/**
	 * 测试API
	 * @param goods
	 * @param request
	 * @param editorValue
	 * @return
	 */
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
	/**
	 * 测试API
	 * @return
	 */
	@PostMapping("/auth")
	@ResponseBody
	public PageResult<AuthorWithBLOBs> getAuth() {
		PageResult<AuthorWithBLOBs> list =new PageResult<AuthorWithBLOBs>();
		AuthorWithBLOBs auth =new AuthorWithBLOBs();
		list.setPageSize(0);
		auth.setColumns("MJHC");
		list = authorWithBLOBsService.queryByPageFront(list, auth);
		return list;
		
	}
	/**
	 * 批量删除
	 * @param list
	 * @return
	 */
	@PostMapping("/batchDelete")
	@ResponseBody
	public ProcessResult<Goods> batchDelete(List<Goods> list) {
		ProcessResult<Goods> res=new ProcessResult<Goods>();
		try {
			int delete = goodsService.batchDelete(list);
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
	public ProcessResult<Goods> batchMarketableUp(List<Goods> list) {
		ProcessResult<Goods> res=new ProcessResult<Goods>();
		try {
			int delete = goodsService.batchMarketableUp(list);
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
	public ProcessResult<Goods> batchMarketableDown(List<Goods> list) {
		ProcessResult<Goods> res=new ProcessResult<Goods>();
		try {
			int delete = goodsService.batchMarketableDown(list);
			if(delete>0) {
				res.setRes(SystemCode.SUCCESS);
				res.setMsg("批量更新完成！");
			}
		} catch (Exception e) {
			logger.error("批量下架商品信息：失败！"+e.getMessage());
		}
		
		return res;
		
	}
	
}
