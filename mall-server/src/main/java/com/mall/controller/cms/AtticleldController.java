package com.mall.controller.cms;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.controller.AbstractController;
import com.mall.entity.cms.Atticleld;
import com.mall.message.ProcessResult;
import com.mall.message.SystemCode;
import com.mall.service.cms.AtticleldService;

/**
 * 文章管理
 * @author Jang
 *
 */
@Controller  
@RequestMapping("/admin/cms/att") 
public class AtticleldController extends AbstractController{
	@Resource
	private AtticleldService atticleldService;
	/**
	 * 删除文章
	 * @param articleId
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public ProcessResult<Atticleld> toDelete(Atticleld att) {
		ProcessResult<Atticleld> res=new ProcessResult<Atticleld>();
		int delete = atticleldService.chengeStatus(att);
		if(delete>0) {
			res.setRes(SystemCode.SUCCESS);
			return res;
		}
		res.setMsg("删除文章失败！");
		return res;
	}
	
}
