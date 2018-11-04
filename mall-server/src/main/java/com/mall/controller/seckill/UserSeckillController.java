package com.mall.controller.seckill;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.controller.AbstractController;
import com.mall.entity.cms.AuthorWithBLOBs;
import com.mall.entity.goods.Goods;
import com.mall.entity.goods.GoodsCategory;
import com.mall.entity.goods.GoodsHistory;
import com.mall.entity.goods.GoodsPrice;
import com.mall.entity.seckill.Seckill;
import com.mall.entity.user.User;
import com.mall.message.SystemCode;
import com.mall.service.Seckill.SeckillService;
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

@Controller
@RequestMapping("mall/seckill")
public class UserSeckillController extends AbstractController {
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
	@Autowired
	private SeckillService seckillService;
	
	@RequestMapping("/{seckillId}/detail")
	public String toGoodsDetail(Model model,@PathVariable("seckillId") String seckillId,HttpServletRequest request) {
		
		if(seckillId == null){
            return "redirect:mall/seckill/list";
        }
		System.out.println(Long.parseLong(seckillId));
        Seckill seckill = seckillService.getById(Long.parseLong(seckillId));
        if(seckill == null){
            return "forward:mall/seckill/list";
        }
        Goods goods =new Goods();
        goods.setGoods_id(seckill.getGoods_id());
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
		
		model.addAttribute("seckill",seckill);
		model.addAttribute("goods", goods);
		model.addAttribute("page", "mall/seckill/seckill_show");
		return "mall/index";
	}
	/**
	 * 秒杀商品列表
	 * @param model
	 * @param goods
	 * @return
	 */
	@RequestMapping("/list")
	public String toGoodsList(Model model, Seckill seckill,PageResult<Seckill> list) {
		
		int pageSize  =  Integer.parseInt(cacheService.getCache(SystemCode.PAGE).get(SystemCode.GOODS_PAGE));
		list.setPageSize(pageSize);
		list = seckillService.queryByPageFront(list, seckill);
		
		for(Seckill s: list.getDataList() ) {
			Goods goods=new Goods();
			goods.setGoods_id(s.getGoods_id());
			goods=goodsService.selectInfo(goods);
			if(Validate.notNull(goods.getGoodsInfo().getAuth_id())) {
				//查询商品作家信息
				AuthorWithBLOBs a=new AuthorWithBLOBs();
				a.setId(goods.getGoodsInfo().getAuth_id());
				a = authorWithBLOBsService.selectInfo(a);
				goods.setAuth(a);
			}
			s.setGoods(goods);
			
		}
		//文件服务器路径
		Map<String, String> cache = cacheService.getCache(SystemCode.FILE_SERVICE);
		String url=cache.get(SystemCode.FILE_SERVICE_URL);
		String port=cache.get(SystemCode.FILE_SERVICE_PORT);
		String filePath=cache.get(SystemCode.FILE_SERVICE_FILES_PATH);
		String fileUrlPrefix=url+":"+port+"/"+filePath;
		model.addAttribute("fileServicePath", fileUrlPrefix);
		model.addAttribute("seckill", seckill);
		model.addAttribute("list", list);
		model.addAttribute("page", "mall/seckill/seckill_list");
		return "mall/index";
	}

}
