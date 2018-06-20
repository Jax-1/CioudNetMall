package com.mall.controller.cms;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mall.controller.AbstractController;
import com.mall.entity.cms.Atticleld;
import com.mall.entity.cms.AtticleldCategory;
import com.mall.entity.cms.AuthorWithBLOBs;
import com.mall.message.SystemCode;
import com.mall.service.cms.AtticleldCategoryService;
import com.mall.service.cms.AtticleldService;
import com.mall.service.cms.AuthorWithBLOBsService;
import com.mall.service.cms.FilePathService;
import com.mall.service.sys.CacheService;
import com.mall.util.PageResult;

/**
 * 商城文章部分控制器
 * @author Jang
 *
 */
@Controller
@RequestMapping("/mall")
public class UserCMSController extends AbstractController{
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
	/**
	 * 作品、资讯、传统文化列表
	 * @param Pid id
	 * @param model
	 * @return
	 */
	@GetMapping("list.do")
	public String toCmsList(String Pid,Model model,PageResult<Atticleld> list,Atticleld att) {
		int pageSize  =  Integer.parseInt(cacheService.getCache(SystemCode.PAGE).get(SystemCode.MALL_ATT_PAGE));
		list.setPageSize(pageSize);
		att.setColumns(Pid);
		list = atticleldService.queryByPageFront(list, att);
		model.addAttribute("list", list);
		logger.info("size:"+Pid);
		logger.info("size:"+list.getDataList().size());
		List<AtticleldCategory> category = atticleldCategoryService.queryAll(Pid);
		List<Atticleld> hotAtt = atticleldService.queryHotAtt(att);
		Map<String, String> cache = cacheService.getCache(SystemCode.FILE_SERVICE);
		String url=cache.get(SystemCode.FILE_SERVICE_URL);
		String port=cache.get(SystemCode.FILE_SERVICE_PORT);
		String filePath=cache.get(SystemCode.FILE_SERVICE_FILES_PATH);
		String fileUrlPrefix=url+":"+port+"/"+filePath;
		//文件服务器路径
		model.addAttribute("fileServicePath", fileUrlPrefix);
		model.addAttribute("hotAtt", hotAtt);
		model.addAttribute("Category", category);
		model.addAttribute("list", list);
		model.addAttribute("Pid", Pid);
		model.addAttribute("page", "/mall/cms/cms_lists");
		return "/mall/index";
	}
	/**
	 * 作品、资讯、传统文化详细视图
	 * @param Pid
	 * @param model
	 * @return
	 */
	@GetMapping("/content.do")
	public String toCmsContent(String Pid,Model model,Atticleld att) {
		model.addAttribute("page", "/mall/cms/cms_show");
		return "/mall/index";
	}
	/**
	 * 作家列表
	 * @param Pid
	 * @param model
	 * @param list
	 * @return
	 */
	@GetMapping("/authlist.do")
	public String toCmsAuthList(String Pid,Model model ,PageResult<AuthorWithBLOBs> list) {
		model.addAttribute("page", "/mall/cms/cms_show");
		return "/mall/index";
	}
	/**
	 * 作家详细页面
	 * @param Pid
	 * @param model
	 * @param auth
	 * @return
	 */
	@GetMapping("/authcontent.do")
	public String toCmsAuthContent(String Pid,Model model,AuthorWithBLOBs auth) {
		model.addAttribute("page", "/mall/cms/cms_show");
		return "/mall/index";
	}

}