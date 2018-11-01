package com.mall.controller.user;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mall.controller.AbstractController;
import com.mall.entity.user.User;
import com.mall.service.sys.CacheService;
import com.mall.service.user.UserLoginService;
import com.mall.util.PageResult;

@Controller
@RequestMapping("/admin")
public class AdminManagementController extends AbstractController{
	@Resource
	private UserLoginService userLoginService;
	@Resource
	private CacheService cacheService;
	
	/**
	 * 获取用户列表
	 * @param model
	 * @param list
	 * @param user
	 * @return
	 */
	@RequestMapping("/user/list")
	public String toUserList(Model model,PageResult<User> list,User user) {
		list = userLoginService.queryByPageFront(list, user);
		
		model.addAttribute("list", list);
		model.addAttribute("page", "admin/user/user_list");
		model.addAttribute("users", "nav-item start active open");
		return "admin/index";
	}

}
