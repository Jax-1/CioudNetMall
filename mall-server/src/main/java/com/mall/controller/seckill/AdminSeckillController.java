package com.mall.controller.seckill;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.controller.AbstractController;
import com.mall.dao.seckill.SeckillMapper;
import com.mall.entity.cms.AuthorWithBLOBs;
import com.mall.entity.goods.Goods;
import com.mall.entity.goods.GoodsCategory;
import com.mall.entity.goods.GoodsList;
import com.mall.entity.goods.GoodsPrice;
import com.mall.entity.seckill.Seckill;
import com.mall.entity.seckill.SeckillList;
import com.mall.message.ProcessResult;
import com.mall.message.SystemCode;
import com.mall.service.Seckill.SeckillService;
import com.mall.service.cms.AuthorWithBLOBsService;
import com.mall.service.goods.GoodsCategoryService;
import com.mall.service.goods.GoodsInfoService;
import com.mall.service.goods.GoodsPriceService;
import com.mall.service.goods.GoodsService;
import com.mall.service.inventory.InventoryService;
import com.mall.service.sys.CacheService;
import com.mall.util.DateFormatUtil;
import com.mall.util.PageResult;
import com.mall.util.Validate;


@Controller
@RequestMapping("admin/seckill")
public class AdminSeckillController extends AbstractController{
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
	@Resource
	private SeckillService seckillService;
	 @Autowired
	 private SeckillMapper seckillDao;
	
	@RequestMapping("/list")
	public String toGoodsList(Model model,PageResult<Seckill> list,Seckill seckill) {
		int pageSize  =  Integer.parseInt(cacheService.getCache(SystemCode.PAGE).get(SystemCode.MALL_ATT_PAGE));
		
		list.setPageSize(pageSize);
		seckillService.queryByPageFront(list, seckill);
		
		for(Seckill s: list.getDataList() ) {
			Goods goods=new Goods();
			goods.setGoods_id(s.getGoods_id());
			goods=goodsService.selectInfo(goods);
			s.setGoods(goods);
			
		}
		
		model.addAttribute("list",list);
		model.addAttribute("seckill", seckill);
		model.addAttribute("page", "admin/seckill/list_seckill");
		model.addAttribute("seckill", "nav-item start active open");
		return "admin/index";
		
	}
	@RequestMapping("/editor")
	public String toGoodsAdd(Model model,Seckill seckill) {
		
		
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
		if(Validate.notNull(seckill)&&Validate.notNull(seckill.getSeckill_id())) {
			
			seckill = seckillService.getById(seckill.getSeckill_id());
			
			Goods goods =new Goods();
			goods.setGoods_id(seckill.getGoods_id());
			
			logger.info("编辑商品信息：ID="+goods.getGoods_id());
			Goods info = goodsService.selectInfo(goods);
			
			
			
			
			Map<String, String> cache = cacheService.getCache(SystemCode.FILE_SERVICE);
			String url=cache.get(SystemCode.FILE_SERVICE_URL);
			String port=cache.get(SystemCode.FILE_SERVICE_PORT);
			String filePath=cache.get(SystemCode.FILE_SERVICE_FILES_PATH);
			String fileUrlPrefix=url+":"+port+"/"+filePath;
			//文件服务器路径
			model.addAttribute("fileServicePath", fileUrlPrefix);
			model.addAttribute("info", info);
			model.addAttribute("seckill_info", seckill);
		}
		model.addAttribute("goodsCategoryList", goodsCategoryList);
		model.addAttribute("authlist", list);
		model.addAttribute("page", "admin/seckill/add_seckill");
		model.addAttribute("seckill", "nav-item start active open");
		return "admin/index";
		
	}
	
	@RequestMapping("/save")
	public String toGoodsSave(Model model,Goods goods,HttpServletRequest request,String editorValue,String type,Seckill seckill) {
		logger.info("是否包邮？"+goods.getGoodsInfo().getExt1());
		logger.info("商品详情："+editorValue);
		logger.info("商品详情："+goods.getDetail_describe());
		if(SystemCode.TYPE_SAVE.equals(type)) {
			//保存操作
			goods.init(goods, request, editorValue);
			logger.info("保存商品信息："+goods.getGoods_name());
			try {
				goodsService.insertSelective(goods);
				GoodsInfoService.insertSelective(goods.getGoodsInfo());
				GoodsPriceService.insertSelective(goods.getGoodsPrice());
				InventoryService.insertSelective(goods.getGoodsInfo().getInventory());
				
				//保存秒杀信息
				seckill.setGoods_id(goods.getGoods_id());
				seckill.setName(goods.getGoods_name());
				seckill.setCreate_time(DateFormatUtil.getDate());
				seckillService.insertSelective(seckill);
				
			} catch (Exception e) {
				logger.error("保存商品信息：失败"+e.getMessage());
				e.printStackTrace();
			}
		}else if(SystemCode.TYPE_UPDATE.equals(type)) {
			//更新操作
			try {
				//goods.setIs_marketable(goods.getIs_marketable()==null?"N":"Y");
				//更新信息
				goods.setRecommend(goods.getRecommend()==null?"N":"Y");
				goods.setClassic(goods.getClassic()==null?"N":"Y");
				goods.setNew_product(goods.getNew_product()==null?"N":"Y");
				goods.getGoodsPrice().setSale(goods.getGoodsPrice().getSale()==null?"N":"Y");
				goods.getGoodsInfo().setExt1(goods.getGoodsInfo().getExt1()==null?"Y":"N");
				
				goodsService.updateByPrimaryKeySelective(goods);
				GoodsInfoService.updateByPrimaryKeySelective(goods.getGoodsInfo());
				GoodsPriceService.updateByPrimaryKeySelective(goods.getGoodsPrice());
				InventoryService.updateByPrimaryKeySelective(goods.getGoodsInfo().getInventory());
				
				//更新秒杀信息
				seckillService.updateByPrimaryKeySelective(seckill);
			} catch (Exception e) {
				logger.error("更新商品信息：失败"+e.getMessage());
				e.printStackTrace();
			}
			
		}
		return "redirect:/admin/seckill/list";
		
	}
	
	/**
	 * 批量删除
	 * @param list
	 * @return
	 */
	@PostMapping("/batchDelete")
	@ResponseBody
	public ProcessResult<Seckill> batchDelete(SeckillList list) {
		System.out.println(list.getList().size());
		ProcessResult<Seckill> res=new ProcessResult<Seckill>();
		try {
			int delete = seckillDao.batchDelete(list.getList());
			if(delete>0) {
				res.setRes(SystemCode.SUCCESS);
				res.setMsg("批量更新完成！");
			}
		} catch (Exception e) {
			logger.error("批量删除商品信息：失败！"+e.getMessage());
		}
		
		return res;
		
	}
	


}
