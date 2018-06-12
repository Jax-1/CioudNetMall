package com.mall.controller.cms;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller  
@RequestMapping("/admin")
public class AdminCMSController {
	@RequestMapping("/ue")
	public String toIndex() {
		return "/admin/add_cms";
	}

}
