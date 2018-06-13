package com.mall.controller.cms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mall.entity.cms.AtticleldCategory;
import com.mall.service.cms.AtticleldCategoryService;
import com.mall.util.Validate;

@Controller  
@RequestMapping("/admin/cms")
public class AdminCMSController {
	@Resource
	private AtticleldCategoryService atticleldCategoryService;
	/**
	 * 添加文章
	 * @return
	 */
	@RequestMapping("/add.do")
	public String toIndex(String id ,Model model) {
		List<AtticleldCategory> list = atticleldCategoryService.queryAll(id);
		model.addAttribute("Category", list);
		System.out.println();
		System.out.println(id);
		//名家荟萃
		if(id!=null&&id.equals("04")) {
			model.addAttribute("page", "/admin/cms/add_mingjia");
			model.addAttribute("content", "nav-item start active open");
		}
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
	 * 列表
	 * @param str
	 * @return
	 */
	@RequestMapping("/list.do")
	public String toCMSList(String id ,Model model) {
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
