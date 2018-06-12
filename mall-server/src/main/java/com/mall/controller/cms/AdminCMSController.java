package com.mall.controller.cms;

import java.util.List;

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
