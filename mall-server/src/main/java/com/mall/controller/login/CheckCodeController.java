package com.mall.controller.login;

import java.awt.image.RenderedImage;
import java.io.IOException;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.controller.AbstractController;
import com.mall.message.ProcessResult;
import com.mall.message.SystemCode;
import com.mall.util.CodeUtil;
import com.mall.util.Validate;

@Controller
@RequestMapping("/code")
public class CheckCodeController extends AbstractController{
	@PostMapping("/getcode")
	@ResponseBody
	public ProcessResult ProcessCode(HttpServletRequest req,HttpServletResponse res) {
		ProcessResult process = new ProcessResult();
		// 调用工具类生成的验证码和验证码图片
        Map<String, Object> codeMap = CodeUtil.generateCodeAndPic();

        // 将四位数字的验证码保存到Session中。
        HttpSession session = req.getSession();
        session.setAttribute("code", codeMap.get("code").toString());

        // 禁止图像缓存。
        res.setHeader("Pragma", "no-cache");
        res.setHeader("Cache-Control", "no-cache");
        res.setDateHeader("Expires", -1);

        res.setContentType("image/jpeg");

        // 将图像输出到Servlet输出流中。
        ServletOutputStream sos;
        try {
            sos = res.getOutputStream();
            ImageIO.write((RenderedImage) codeMap.get("codePic"), "jpeg", sos);
            sos.close();
        } catch (IOException e) {
            return process;
        }
        process.setRes(SystemCode.SUCCESS);
        return process;
        

	}
	@PostMapping("/checkcode")
	@ResponseBody
	public ProcessResult checkCode(String code,HttpServletRequest req, HttpServletResponse res) {
		ProcessResult process=new ProcessResult();
		if(!Validate.notNull(code)) {
			process.setMsg("验证码不得为空！");
			return process;
		}
		// 验证验证码
        String sessionCode = req.getSession().getAttribute("code").toString();
        if (code != null && !"".equals(code) && sessionCode != null && !"".equals(sessionCode)) {
            if (code.equalsIgnoreCase(sessionCode)) {
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
