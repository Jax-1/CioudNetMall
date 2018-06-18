package com.mall.entity.cms;

import javax.servlet.http.HttpServletRequest;

import com.mall.message.SystemCode;
import com.mall.util.DateFormatUtil;
import com.mall.util.SessionUtil;
import com.mall.util.UUIDUtil;
import com.mall.util.Validate;

public class AuthorWithBLOBs extends Author {
    private String introduction;

	private String content;
	/**
	 * 关联分类表
	 */
	private AtticleldCategory atticleldCategory;
	
	public AtticleldCategory getAtticleldCategory() {
		return atticleldCategory;
	}

	public void setAtticleldCategory(AtticleldCategory atticleldCategory) {
		this.atticleldCategory = atticleldCategory;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 初始化
	 * @param auth
	 * @param request
	 * @param editorValue
	 * @return
	 */
	public static AuthorWithBLOBs init(AuthorWithBLOBs auth,HttpServletRequest request,String editorValue) {
		auth.setId(UUIDUtil.getUUID());
		auth.setStatus(SystemCode.STATUS_Y);
		if(Validate.notNull(SessionUtil.getAdminUser(request))) {
			//创建人
			auth.setCreateby(SessionUtil.getAdminUser(request).getDescription());
		}
		
		auth.setCreateat(DateFormatUtil.getDate());
		auth.setContent(editorValue);
		//初始化点赞数，浏览数
		auth.setViewCount(0);
		auth.setLikeCount(0);
		return auth;
		
	}
	
}