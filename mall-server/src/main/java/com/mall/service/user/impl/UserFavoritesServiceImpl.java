package com.mall.service.user.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mall.dao.base.IBaseDao;
import com.mall.dao.user.UserFavoritesMapper;
import com.mall.dao.user.UserMapper;
import com.mall.entity.cms.AuthorWithBLOBs;
import com.mall.entity.goods.Goods;
import com.mall.entity.user.User;
import com.mall.entity.user.UserFavorites;
import com.mall.message.ProcessResult;
import com.mall.service.BaseServiceImpl;
import com.mall.service.cms.AuthorWithBLOBsService;
import com.mall.service.goods.GoodsService;
import com.mall.service.sys.CacheService;
import com.mall.service.user.UserFavoritesService;
import com.mall.util.PageResult;
import com.mall.util.Validate;

@Service
public class UserFavoritesServiceImpl extends BaseServiceImpl<UserFavorites> implements UserFavoritesService {
	@Resource
	private UserMapper userMapper;
	@Resource
	private UserFavoritesMapper userFavoritesMapper;
	@Resource
	private GoodsService goodsService;
	@Resource
	private CacheService cacheService;
	@Resource
	private AuthorWithBLOBsService authorWithBLOBsService;
	@Override
	protected IBaseDao<UserFavorites> getMapper() {
		return userFavoritesMapper;
	}
	@Override
	public ProcessResult<UserFavorites> addFavorites(UserFavorites userFavorites,User user) {
		ProcessResult<UserFavorites> res=new ProcessResult<UserFavorites>();
		if(userFavorites==null) {
			res.setMsg("数据错误，添加收藏夹失败！");
			return res;
		}
		userFavorites.setUser_id(user.getUser_name());
		try {
			int insertSelective = userFavoritesMapper.insertSelective(userFavorites);
			if(insertSelective>0) {
				//添加收藏夹成功
				return ProcessResult.success(res);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		res.setMsg("添加收藏夹失败！");
		return res;
	}
	@Override
	public ProcessResult<UserFavorites> removeFavorites(UserFavorites userFavorites) {
		ProcessResult<UserFavorites> res=new ProcessResult<UserFavorites>();
		if(userFavorites==null) {
			res.setMsg("数据错误，移出收藏夹失败！");
			return res;
		}
		try {
			userFavorites.setDel("Y");
			int insertSelective = userFavoritesMapper.updateByPrimaryKeySelective(userFavorites);
			if(insertSelective>0) {
				//移出收藏夹成功
				return ProcessResult.success(res);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		res.setMsg("移出收藏夹失败！");
		return res;
	}
	@Override
	public UserFavorites getFavoritesDetail(UserFavorites userFavorites) {
		if(userFavorites==null) {
			return userFavorites;
		}
		UserFavorites favorites = userFavoritesMapper.selectByPrimaryKey(userFavorites.getId());
		Goods goods =new Goods();
		goods.setGoods_id(favorites.getGoods_id());
		goodsService.selectInfo(goods);
		if(Validate.notNull(goods.getGoodsInfo().getAuth_id())) {
			//查询商品作家信息
			AuthorWithBLOBs a=new AuthorWithBLOBs();
			a.setId(goods.getGoodsInfo().getAuth_id());
			a = authorWithBLOBsService.selectInfo(a);
			goods.setAuth(a);
		}
		favorites.setGoods(goods);
		return favorites;
	}
	
}
