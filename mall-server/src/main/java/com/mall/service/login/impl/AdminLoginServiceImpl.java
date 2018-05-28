package com.mall.service.login.impl;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mall.code.ProcessMassage;
import com.mall.code.ProcessResult;
import com.mall.code.SystemCode;
import com.mall.dao.login.AdminMapper;
import com.mall.entity.login.Admin;
import com.mall.service.login.AdminLoginService;
import com.mall.util.Validate;
import com.mall.util.MD5Util;
import com.mall.util.SessionUtil;


@Service
public class AdminLoginServiceImpl implements AdminLoginService {
	@Resource
	private AdminMapper adminMapper;
	@Override
	public ProcessResult<Admin> login(Admin admin,HttpServletRequest request) {
		ProcessResult<Admin> result = new ProcessResult<Admin>();
		Admin model = adminMapper.selectByPrimaryKey(admin.getUser_name());
		//状态验证
		if(SystemCode.STATUS_N.equals(model.getStatus())) {
			result.setMsg(SystemCode.USER_STATUS_N_MSG);
			return result;
		}
		if(Validate.notNull(model)) {
			//密码验证
			String password = MD5Util.encoder(admin.getPassword(),model.getRand());
			if(password.equals(model.getPassword())) {
				SessionUtil.setAdminUser(request, model);
				result.setRes(ProcessMassage.SUCCESS);
				result.setMsg(SystemCode.LOGIN_SUCESS);
			}else {
				result.setRes(ProcessMassage.FAILURE);
				result.setMsg(SystemCode.LOGIN_FAILURE);
			}
			
		}
		return result;
	}

	@Override
	public ProcessResult forgotPassword(Admin admin) {
		
		return null;
	}

	@Override
	public ProcessResult loginOut(HttpServletRequest request) {
		request.getSession().invalidate();
		return new ProcessResult<Admin>(ProcessMassage.SUCCESS,SystemCode.LOGIN_OUT_SUCESS);
	}

	@Override
	@Transactional
	public ProcessResult<Admin> registered(Admin admin) {
		adminMapper.insert(admin);
		return new ProcessResult<Admin>(ProcessMassage.SUCCESS,SystemCode.LOGIN_OUT_SUCESS);
	}

}
