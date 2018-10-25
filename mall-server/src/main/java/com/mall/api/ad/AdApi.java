package com.mall.api.ad;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.api.BaseAPI;
import com.mall.entity.ad.Ad;
import com.mall.service.ad.AdService;
import com.mall.util.PageResult;

@Controller
@RequestMapping("api/ad")
public class AdApi extends BaseAPI {
	@Resource
	private AdService adService;
	
	@GetMapping("goods")
	@ResponseBody
	public PageResult<Ad> getAd() {
		//获取广告信息
		PageResult<Ad> adList=new PageResult<Ad>();
		Ad ad =new Ad();
		Byte state=1;
		ad.setState(state);
		adList=adService.queryByPageFront(adList,ad);
		return adList;
	}
}
