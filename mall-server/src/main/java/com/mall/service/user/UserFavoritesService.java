package com.mall.service.user;

import java.util.List;

import com.mall.entity.user.User;
import com.mall.entity.user.UserFavorites;
import com.mall.message.ProcessResult;
import com.mall.service.IBaseService;

public interface UserFavoritesService extends IBaseService<UserFavorites>{
	
	/**
	 * 加入收藏夹
	 * @param userFavorites
	 * @return
	 */
	public ProcessResult<UserFavorites> addFavorites(UserFavorites userFavorites,User user);
	
	/**
	 * 移出收藏夹
	 * @param userFavorites
	 * @return
	 */
	public ProcessResult<UserFavorites> removeFavorites(UserFavorites userFavorites);
	
	
	/**
	 * 收藏夹详情
	 * @param userFavorites
	 * @return
	 */
	public UserFavorites getFavoritesDetail(UserFavorites userFavorites);
}
