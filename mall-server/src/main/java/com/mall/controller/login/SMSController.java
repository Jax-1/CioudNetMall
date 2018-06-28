package com.mall.controller.login;

import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.mall.controller.AbstractController;
import com.mall.message.ProcessResult;
import com.mall.message.SystemCode;
import com.mall.service.sys.CacheService;
import com.mall.util.SmsEntity;
import com.mall.util.SmsUtil;
import com.mall.util.Validate;

@Controller
@RequestMapping("/sms")
public class SMSController extends AbstractController{
	@Resource
	private CacheService cacheService;
	/**
	 * 获取注册短信验证码
	 * @param phone
	 * @param req
	 * @return
	 */
	@RequestMapping("/getcode")
	@ResponseBody
	public SendSmsResponse getSMSCode(String phone,HttpServletRequest req) {
		Map<String, String> sms = cacheService.getCache(SystemCode.SMS);
		String code=Integer.toString(new Random().nextInt(999999));
		
		SmsEntity smsentity=new SmsEntity(sms.get(SystemCode.ACCESSSKEYID),sms.get(SystemCode.ACCESSSKEYSECRET),sms.get(SystemCode.SIGNNAME),sms.get(SystemCode.REGISTERED_TEMPLATECODE),phone,code);
		SendSmsResponse sendSms=null;
		try {
			sendSms = SmsUtil.sendSms(smsentity);
			HttpSession session = req.getSession();
	        session.removeAttribute("phonecode");
	        session.setAttribute("phonecode", code);
	        logger.info("获取注册短信验证："+sendSms.getCode());
		} catch (ClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sendSms;
		
	}
	/**
	 * 校验手机验证码
	 * @param phonecode
	 * @param req
	 * @param res
	 * @return
	 */
	@PostMapping("/checkcode")
	@ResponseBody
	@SuppressWarnings("rawtypes")
	public ProcessResult checkCode(String phonecode,HttpServletRequest req, HttpServletResponse res) {
		ProcessResult process=new ProcessResult();
		if(!Validate.notNull(phonecode)) {
			process.setMsg("验证码不得为空！");
			return process;
		}
		// 验证验证码
        String sessionCode = req.getSession().getAttribute("phonecode").toString();
        if (phonecode != null && !"".equals(phonecode) && sessionCode != null && !"".equals(sessionCode)) {
            if (phonecode.equalsIgnoreCase(sessionCode)) {
            	process.setRes(SystemCode.SUCCESS);
            	
            } else {
            	process.setMsg("验证失败！");
            }
        } else {
        	process.setMsg("验证失败！");
        }
    
		return process;
		
	}

}
