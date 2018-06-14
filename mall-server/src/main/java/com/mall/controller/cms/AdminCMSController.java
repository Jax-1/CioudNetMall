package com.mall.controller.cms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.pagehelper.Page;
import com.mall.entity.cms.Atticleld;
import com.mall.entity.cms.AtticleldCategory;
import com.mall.message.SystemCode;
import com.mall.service.cms.AtticleldCategoryService;
import com.mall.service.cms.AtticleldService;
import com.mall.service.sys.CacheService;
import com.mall.util.Validate;

@Controller  
@RequestMapping("/admin/cms")
public class AdminCMSController {
	@Resource
	private AtticleldCategoryService atticleldCategoryService;
	@Resource
	private AtticleldService atticleldService;
	@Resource
	private CacheService cacheService;
	/**
	 * 添加文章
	 * @return
	 */
	@RequestMapping("/add.do")
	public String toIndex(String id ,Model model) {
		List<AtticleldCategory> list = atticleldCategoryService.queryAll(id);
		model.addAttribute("Category", list);
		/**
		 * CMS列展开
		 */
		Map<String,String > map =new HashMap<String,String>();
		String mainClass = null;
		map.put("ZPJJ", "");
		map.put("ZXZX", "");
		map.put("CTWH", "");
		//01:作品集锦，02：资讯中心，03：传统文化
		switch (id) {
		case "01":
			map.put("ZPJJ", "start active open");
			mainClass="作品集锦";
			break;
		case "02":
			map.put("ZXZX", "start active open");
			mainClass="资讯中心";
			break;
		case "03":
			map.put("CTWH", "start active open");
			mainClass="传统文化";
			break;

		default:
			break;
		}
		model.addAttribute("lineopen", map);
		//主分类默认选中,及显示
		model.addAttribute("main", mainClass);
		model.addAttribute("mainid", id);
		model.addAttribute("page", "/admin/cms/add_cms");
		model.addAttribute("content", "nav-item start active open");
		return "/admin/index";
	}
	@RequestMapping("/save")
	public String toSave(String id ,Model model) {
		return "/admin/cms/add_cms";
	}
	/**
	 * 添加名家
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/addwriter.do")
	public String toAddWriter(String id ,Model model) {
		model.addAttribute("page", "/admin/cms/add_mingjia");
		model.addAttribute("content", "nav-item start active open");
		return "/admin/index";
	}
	/**
	 * 名家荟萃列表
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/writerlist.do")
	public String toWriterList(String id ,Model model) {
		model.addAttribute("page", "/admin/cms/list_cms");
		model.addAttribute("content", "nav-item start active open");
		return "/admin/index";
	}
	/**
	 * 文章列表
	 * @param str
	 * @return
	 */
	@GetMapping("/list.do")
	public String toCMSList(String id ,Model model,Integer pageNow,String operation) {
		/**
		 * 分页处理
		 */
		if(!Validate.notNull(pageNow)||pageNow<=0) {
			//初始化显示第一页
			pageNow=1;
		}
		int pageSize  =  Integer.parseInt(cacheService.getCache(SystemCode.PAGE).get(SystemCode.ATT_PAGE));
		Page<Atticleld> page = atticleldService.queryList( id,pageNow,pageSize);
		
		model.addAttribute("id", id);
		model.addAttribute("list", page.getResult());
		//总页数
		model.addAttribute("pageCount", page.getPages());
		model.addAttribute("pageNow", pageNow);
		model.addAttribute("page", "/admin/cms/list_cms");
		model.addAttribute("content", "nav-item start active open");
		return "/admin/index";
	}
	/**
	 * 分类
	 * @return
	 */
	@RequestMapping("/classify.do")
	public String toClassify(Model model) {
		List<AtticleldCategory> list = atticleldCategoryService.queryAll(null);
		List<AtticleldCategory> mjhc=new ArrayList<AtticleldCategory>();
		List<AtticleldCategory> zpjj=new ArrayList<AtticleldCategory>();
		List<AtticleldCategory> zxzx=new ArrayList<AtticleldCategory>();
		List<AtticleldCategory> ctwh=new ArrayList<AtticleldCategory>();
		for(AtticleldCategory att:list) {
			if(Validate.notNull(att)&&"01".equals(att.getParentId())) {
				zpjj.add(att);
			}else if(Validate.notNull(att)&&"02".equals(att.getParentId())) {
				zxzx.add(att);
			}else if(Validate.notNull(att)&&"03".equals(att.getParentId())) {
				ctwh.add(att);
			}else if(Validate.notNull(att)&&"04".equals(att.getParentId())) {
				mjhc.add(att);
			}
		}
		model.addAttribute("mjhc", mjhc);
		model.addAttribute("zpjj", zpjj);
		model.addAttribute("zxzx", zxzx);
		model.addAttribute("ctwh", ctwh);
		model.addAttribute("page", "/admin/cms/classify_cms");
		model.addAttribute("content", "nav-item start active open");
		return "/admin/index";
	}
	

}
