package com.mall.service.order.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mall.dao.base.IBaseDao;
import com.mall.dao.order.FavoritesMapper;
import com.mall.entity.order.Favorites;
import com.mall.service.BaseServiceImpl;
import com.mall.service.order.FavoritesService;
import com.mall.util.PageResult;

@Service
public class FavoritesServiceImpl extends BaseServiceImpl<Favorites> implements FavoritesService {
	@Resource
	private FavoritesMapper favoritesMapper;
	@Override
	protected IBaseDao<Favorites> getMapper() {
		return favoritesMapper;
	}

	
}
