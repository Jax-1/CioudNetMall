package com.mall.api.user;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.api.BaseAPI;
import com.mall.entity.goods.Goods;
import com.mall.entity.user.User;
import com.mall.entity.user.UserFavorites;
import com.mall.message.ProcessResult;
import com.mall.message.SystemCode;
import com.mall.service.goods.GoodsService;
import com.mall.service.sys.CacheService;
import com.mall.service.user.UserFavoritesService;
import com.mall.util.PageResult;
import com.mall.util.SessionUtil;

@Controller
@RequestMapping("api/user/favorite")
public class UserFavoritesApi extends BaseAPI {
	@Resource
	private UserFavoritesService userFavoritesService;
	@Resource
	private CacheService cacheService;
	@Resource
	private GoodsService goodsService;
	
	/**
	 * 保存收藏夹
	 * @param request
	 * @param userFavorites
	 * @return
	 */
	@PostMapping("/save")
	@ResponseBody
	public ProcessResult<UserFavorites> addFavorites(HttpServletRequest request,UserFavorites userFavorites){
		User user = SessionUtil.getUser(request);
		return userFavoritesService.addFavorites(userFavorites, user);
		
	}
	/**
	 * 移出收藏夹
	 * @param request
	 * @param userFavorites
	 * @return
	 */
	@PostMapping("/delete")
	@ResponseBody
	public ProcessResult<UserFavorites> removeFavorites(UserFavorites userFavorites){
		return userFavoritesService.removeFavorites(userFavorites);
		
	}
	/**
	 * 获取收藏夹详情
	 * @param request
	 * @param userFavorites
	 * @return
	 */
	@GetMapping("/detail")
	@ResponseBody
	public UserFavorites getFavoritesDetail(UserFavorites userFavorites){
		return userFavoritesService.getFavoritesDetail(userFavorites);
		
	}
	/**
	 * 获取收藏夹列表
	 * @param request
	 * @param userFavorites
	 * @return
	 */
	@GetMapping("/list")
	@ResponseBody
	public PageResult<UserFavorites> getFavoritesDetail(HttpServletRequest request,UserFavorites userFavorites,PageResult<UserFavorites> list){
		if(userFavorites==null) {
			User user = SessionUtil.getUser(request);
			userFavorites.setUser_id(user.getUser_name());
		}
		int pageSizeShuf  =  Integer.parseInt(cacheService.getCache(SystemCode.PAGE).get(SystemCode.MALL_GOODS_SHUF_PAGE));
		list.setPageSize(pageSizeShuf);
		list=userFavoritesService.queryByPageFront(list,userFavorites);
		for(UserFavorites UserFavorites:list.getDataList()) {
			Goods goods =new Goods();
			goods.setGoods_id(UserFavorites.getGoods_id());
			goods=goodsService.selectInfo(goods);
			UserFavorites.setGoods(goods);
			
		}
		return list;
		
	}
	

}
