package com.mall.controller.goods;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.controller.AbstractController;
import com.mall.entity.goods.GoodsCategory;
import com.mall.message.ProcessResult;
import com.mall.message.SystemCode;
import com.mall.service.goods.GoodsCategoryService;

/**
 * 商品分类服务类
 * @author Jang
 *
 */
@Controller
@RequestMapping("/admin/goods/category")
public class GoodsCategoryController extends AbstractController{
	@Resource
	private GoodsCategoryService goodsCategoryService;
	
	@PostMapping("/save")
	@ResponseBody
	public ProcessResult<GoodsCategory> save(GoodsCategory goodsCategory,HttpServletRequest request){
		ProcessResult<GoodsCategory> res=new ProcessResult<GoodsCategory>();
		goodsCategory=GoodsCategory.init(request, goodsCategory);
		try {
			goodsCategoryService.insertSelective(goodsCategory);
			res=ProcessResult.success(res);
		} catch (Exception e) {
			logger.error("保存商品分类信息：失败！"+e.getMessage());
			e.printStackTrace();
		}
		return res;
	}
	@PostMapping("/delete")
	@ResponseBody
	public ProcessResult<GoodsCategory> delete(GoodsCategory goodsCategory){
		logger.info("删除分类信息："+goodsCategory.getCategory_id());
		goodsCategoryService.chengeStatus(goodsCategory);
		return new ProcessResult<GoodsCategory>(SystemCode.SUCCESS,"删除商品分类信息成功！");
	}
	@PostMapping("/update")
	@ResponseBody
	public ProcessResult<GoodsCategory> update(GoodsCategory goodsCategory){
		ProcessResult<GoodsCategory> res=new ProcessResult<GoodsCategory>();
		try {
			goodsCategoryService.updateByPrimaryKeySelective(goodsCategory);
			res=ProcessResult.success(res);
		} catch (Exception e) {
			logger.error("更新商品分类信息：失败！"+e.getMessage());
			e.printStackTrace();
		}
		return res;
	}
	
	/**
	 * 测试API获取分类信息
	 * @return
	 */
	@PostMapping("/de")
	@ResponseBody
	public List<GoodsCategory> getTest(){
		
		return goodsCategoryService.getGoodsCategoryList(null);
		
	}

}
