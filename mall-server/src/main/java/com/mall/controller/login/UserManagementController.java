package com.mall.controller.login;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mall.controller.AbstractController;
import com.mall.entity.login.User;
import com.mall.util.SessionUtil;

/**
 * 用户信息管理
 * @author Jang
 *
 */
@Controller
@RequestMapping("/user")
public class UserManagementController extends AbstractController{
	@RequestMapping("/manager")
	public String toUserManager(Model model,HttpServletRequest request) {
		model.addAttribute("page", "mall/login/my_center");
		model.addAttribute("manager", "info");
		return "mall/index";
	}
	@RequestMapping("/modifypwd")
	public String toUserModifyPwd(Model model,HttpServletRequest request) {
		model.addAttribute("page", "mall/login/my_center");
		model.addAttribute("manager", "modify_pwd");
		return "mall/index";
	}

}
