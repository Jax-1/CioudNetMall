package com.mall.controller.cms;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.controller.AbstractController;
import com.mall.dao.cms.AuthorMapper;
import com.mall.entity.cms.AuthorWithBLOBs;
import com.mall.message.ProcessResult;
import com.mall.message.SystemCode;

@Controller  
@RequestMapping("/admin/cms/auth")
public class AuthorController extends AbstractController{
	@Resource
	private AuthorMapper authorMapper;
	/**
	 * 删除作家
	 * @param id
	 * @return
	 */
	@PostMapping("/delete")
	@ResponseBody
	public ProcessResult<AuthorWithBLOBs> deleteAuth(AuthorWithBLOBs auth) {
		ProcessResult<AuthorWithBLOBs> res=new ProcessResult<AuthorWithBLOBs>();
		int chengeStatus = authorMapper.chengeStatus(auth);
		if(chengeStatus>0) {
			res.setRes(SystemCode.SUCCESS);
			return res;
		}
		res.setMsg("删除作家信息失败！");
		return res;
		
	}

}
