package com.mall.controller.cms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mall.entity.cms.AtticleldCategory;
import com.mall.service.cms.AtticleldCategoryService;

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
		return "/admin/cms/add_cms";
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
		return "/admin/cms/add_cms";
	}
	/**
	 * 分类
	 * @return
	 */
	@RequestMapping("/classify.do")
	public String toClassify(Model model) {
		return "/admin/cms/add_cms";
	}
	

}
