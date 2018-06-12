package com.mall.controller.cms;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller  
@RequestMapping("/admin")
public class AdminCMSController {
	/**
	 * 添加文章
	 * @return
	 */
	@RequestMapping("/add.do")
	public String toIndex(String id) {
		return "/admin/cms/add_cms";
	}
	/**
	 * 列表
	 * @param str
	 * @return
	 */
	@RequestMapping("/list.do")
	public String toCMSList(String id) {
		return "/admin/cms/add_cms";
	}
	/**
	 * 分类
	 * @return
	 */
	@RequestMapping("/classify.do")
	public String toClassify() {
		return "/admin/cms/add_cms";
	}
	

}
