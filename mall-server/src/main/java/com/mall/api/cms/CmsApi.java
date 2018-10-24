package com.mall.api.cms;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.api.BaseAPI;
import com.mall.entity.cms.Atticleld;
import com.mall.entity.cms.AtticleldCategory;
import com.mall.entity.cms.AuthorWithBLOBs;
import com.mall.entity.goods.Goods;
import com.mall.entity.goods.GoodsInfo;
import com.mall.message.SystemCode;
import com.mall.service.cms.AtticleldCategoryService;
import com.mall.service.cms.AtticleldService;
import com.mall.service.cms.AuthorWithBLOBsService;
import com.mall.service.cms.FilePathService;
import com.mall.service.goods.GoodsService;
import com.mall.service.sys.CacheService;
import com.mall.util.PageResult;

@Controller
@RequestMapping("api/cms")
public class CmsApi extends BaseAPI {
	@Resource
	private CacheService cacheService;
	@Resource
	private AtticleldCategoryService atticleldCategoryService;
	@Resource
	private AtticleldService atticleldService;
	@Resource
	private FilePathService filePathService;
	@Resource
	private AuthorWithBLOBsService authorWithBLOBsService;
	@Resource
	private GoodsService goodsService;
	/**
	 * 作品、资讯、传统文化列表
	 * @param Pid id
	 * @param model
	 * @return
	 */
	@GetMapping("list/{Pid}")
	@ResponseBody
	public PageResult<Atticleld> toCmsList(@PathVariable("Pid")String Pid,Model model,PageResult<Atticleld> list,Atticleld att) {
		int pageSize  =  Integer.parseInt(cacheService.getCache(SystemCode.PAGE).get(SystemCode.MALL_ATT_PAGE));
		list.setPageSize(pageSize);
		att.setColumns(Pid);
		list = atticleldService.queryByPageFront(list, att);
		
		
		
		return list;
	}
	/**
	 * CMS分类
	 * @param Pid
	 * @return
	 */
	@GetMapping("/{Pid}")
	@ResponseBody
	public List<AtticleldCategory> getCMScategory(@PathVariable("Pid")String Pid) {
		List<AtticleldCategory> category = atticleldCategoryService.queryAll(Pid);
		return category;
	}
	/**
	 * 作品、资讯、传统文化详细视图
	 * @param Pid
	 * @param model
	 * @return
	 */
	@GetMapping("/{Pid}/content")
	@ResponseBody
	public Atticleld toCmsContent(@PathVariable("Pid")String Pid,Model model,Atticleld att) {
		//文章信息
		Atticleld att1 = atticleldService.selectInfo(att);
		//增加浏览数
		att1.setViewCount(att1.getViewCount()+1);
		atticleldService.updateLikeAndViewCount(att1);
		return att1;
	}
	/**
	 * 作家列表
	 * @param Pid
	 * @param model
	 * @param list
	 * @return
	 */
	@GetMapping("/auth/list")
	@ResponseBody
	public PageResult<AuthorWithBLOBs> toCmsAuthList(Model model ,PageResult<AuthorWithBLOBs> list,AuthorWithBLOBs auth) {
		int pageSize  =  Integer.parseInt(cacheService.getCache(SystemCode.PAGE).get(SystemCode.MALL_AUT_PAGE));
		list.setPageSize(pageSize);
		auth.setColumns("MJHC");
		list = authorWithBLOBsService.queryByPageFront(list, auth);
		logger.info("info:"+list.getDataList().size());
		
		
		return list;
	}
	/**
	 * 作家详细页面
	 * @param Pid
	 * @param model
	 * @param auth
	 * @return
	 */
	@GetMapping("/auth/content")
	@ResponseBody
	public AuthorWithBLOBs toCmsAuthContent(String Pid,Model model,AuthorWithBLOBs auth) {
		auth = authorWithBLOBsService.selectInfo(auth);
		logger.info("增加作家查看次数："+auth.getAuthorname());
		//增加查看次数
		auth.setViewCount(auth.getViewCount()+1);
		authorWithBLOBsService.updateLikeAndViewCount(auth);
		
		return auth;
	}
	/**
	 * 获取推荐作家
	 * @return
	 */
	@GetMapping("/auth/recommend")
	@ResponseBody
	public List<AuthorWithBLOBs> getRecommendAtt() {
		//推荐作家
		List<AuthorWithBLOBs> auth = authorWithBLOBsService.queryRecommendAtt(new AuthorWithBLOBs());
		logger.info("获取推荐作家："+auth.size());
		return auth;
	}

}
