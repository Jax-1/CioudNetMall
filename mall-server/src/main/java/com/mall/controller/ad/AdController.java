package com.mall.controller.ad;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.controller.AbstractController;
import com.mall.dao.ad.AdPositionMapper;
import com.mall.entity.ad.Ad;
import com.mall.entity.ad.AdPosition;
import com.mall.entity.login.Admin;
import com.mall.entity.order.Order;
import com.mall.message.ProcessResult;
import com.mall.message.SystemCode;
import com.mall.service.ad.AdService;
import com.mall.service.sys.CacheService;
import com.mall.util.DateFormatUtil;
import com.mall.util.PageResult;
import com.mall.util.SessionUtil;

import org.springframework.ui.Model;

@Controller 
@RequestMapping("/admin/ad")
public class AdController extends AbstractController{
	@Resource
	private AdService adService;
	@Resource
	private AdPositionMapper adPositionMapper;
	@Resource
	private CacheService cacheService;
	@RequestMapping("/list")
	public String toAdList(Model model ,PageResult<Ad> list,Ad ad) {
		list=adService.queryByPageFront(list,ad);
		//文件服务器路径
		Map<String, String> cache = cacheService.getCache(SystemCode.FILE_SERVICE);
		String url=cache.get(SystemCode.FILE_SERVICE_URL);
		String port=cache.get(SystemCode.FILE_SERVICE_PORT);
		String filePath=cache.get(SystemCode.FILE_SERVICE_FILES_PATH);
		String fileUrlPrefix=url+":"+port+"/"+filePath;
		
		model.addAttribute("fileServicePath", fileUrlPrefix);
		model.addAttribute("list", list);
		model.addAttribute("page", "admin/ad/lunbotu");
		return "admin/index";
		
	}
	@RequestMapping("/add")
	public String toAdAdd(Model model,Ad ad) {
		//获取广告位
		List<AdPosition> list = adPositionMapper.getAdPositionList(new AdPosition());
		ad=adService.selectInfo(ad);
		//文件服务器路径
		Map<String, String> cache = cacheService.getCache(SystemCode.FILE_SERVICE);
		String url=cache.get(SystemCode.FILE_SERVICE_URL);
		String port=cache.get(SystemCode.FILE_SERVICE_PORT);
		String filePath=cache.get(SystemCode.FILE_SERVICE_FILES_PATH);
		String fileUrlPrefix=url+":"+port+"/"+filePath;
		model.addAttribute("fileServicePath", fileUrlPrefix);
		model.addAttribute("ad", ad);
		model.addAttribute("list", list);
		model.addAttribute("page", "admin/ad/add_lunbotu");
		return "admin/index";
		
	}
	@RequestMapping("/save")
	public String toAdsave(Model model,Ad ad,HttpServletRequest request,String actionsave) {
		if(SystemCode.TYPE_SAVE.equals(actionsave)) {
			Admin adminUser = SessionUtil.getAdminUser(request);
			ad.setAdmin_name(adminUser.getAdmin_name());
			ad.setCreate_time(DateFormatUtil.getDate());
			try {
				adService.insertSelective(ad);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}else if(SystemCode.TYPE_UPDATE.equals(actionsave)) {
			try {
				adService.updateByPrimaryKey(ad);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
			
		
		model.addAttribute("page", "admin/ad/lunbotu");
		return "admin/index";
		
	}
	/**
	 * 更改广告状态
	 * @param ad
	 * @return
	 */
	@RequestMapping("/chengeStatus")
	@ResponseBody
	public ProcessResult<Ad> chengeStatus(Ad ad){
		ProcessResult<Ad> res=new ProcessResult<Ad>();
		int updateAdStatus = adService.updateAdStatus(ad);
		if(updateAdStatus>0) {
			res=ProcessResult.success(res);
			return res;
		}
		return res;
		
	}

}
