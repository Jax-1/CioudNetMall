package com.mall.controller.goods;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.controller.AbstractController;
import com.mall.entity.goods.Goods;
import com.mall.entity.goods.GoodsCategory;
import com.mall.message.ProcessResult;
import com.mall.service.goods.GoodsService;

@Controller
@RequestMapping("/admin/goods")
public class GoodsController extends AbstractController{
	@Resource
	private GoodsService goodsService;
	@PostMapping("/de")
	@ResponseBody
	public ProcessResult<Goods> getTest(Goods goods){
		
		ProcessResult<Goods> res=new ProcessResult<Goods>();
		try {
			goodsService.insertGoods(goods);
			res=ProcessResult.success(res);
		} catch (Exception e) {
			logger.error("保存商品分类信息：失败！"+e.getMessage());
			e.printStackTrace();
		}
		return res;
	}
}
