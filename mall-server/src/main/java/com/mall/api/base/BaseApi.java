package com.mall.api.base;

import java.awt.image.RenderedImage;
import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.api.BaseAPI;
import com.mall.message.ProcessResult;
import com.mall.message.SystemCode;
import com.mall.service.sys.CacheService;
import com.mall.util.CodeUtil;
import com.mall.util.Validate;

/**
 * 工具API
 * @author Jang
 *
 */
@Controller
@RequestMapping("api/base")
public class BaseApi extends BaseAPI {
	@Resource
	private CacheService cacheService;
	
	@GetMapping("filepath")
	@ResponseBody
	public String getFileUrlPrefix() {
		Map<String, String> cache = cacheService.getCache(SystemCode.FILE_SERVICE);
		String url=cache.get(SystemCode.FILE_SERVICE_URL);
		String port=cache.get(SystemCode.FILE_SERVICE_PORT);
		String filePath=cache.get(SystemCode.FILE_SERVICE_FILES_PATH);
		String fileUrlPrefix=url+":"+port+"/"+filePath;
		return fileUrlPrefix;
	}
	/**
	 * 获取图片验证码
	 * @param req
	 * @param res
	 */
	@RequestMapping("/getcode")
	public void ProcessCode(HttpServletRequest req,HttpServletResponse res) {
		// 调用工具类生成的验证码和验证码图片
        Map<String, Object> codeMap = CodeUtil.generateCodeAndPic();

        // 将四位数字的验证码保存到Session中。
        HttpSession session = req.getSession();
        session.removeAttribute("imgcode");
        session.setAttribute("imgcode", codeMap.get("code").toString());
        logger.info("获取图片验证码！");
        // 禁止图像缓存。
        res.setHeader("Pragma", "no-cache");
        res.setHeader("Cache-Control", "no-cache");
        res.setDateHeader("Expires", -1);

        res.setContentType("image/jpeg");

        
        try {
        	ServletOutputStream  sos = res.getOutputStream();
			ImageIO.write((RenderedImage) codeMap.get("codePic"), "jpeg", sos);
			 sos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
       
        

	}
	/**
	 * 校验图片验证码
	 * @param imgcode
	 * @param req
	 * @param res
	 * @return
	 */
	@PostMapping("/checkcode")
	@ResponseBody
	@SuppressWarnings("rawtypes")
	public ProcessResult checkCode(String imgcode,HttpServletRequest req, HttpServletResponse res) {
		
		ProcessResult process=new ProcessResult();
		// 验证图片验证码
        String sessionCode = req.getSession().getAttribute("imgcode").toString();
        if (Validate.notNull(imgcode)&&Validate.notNull(sessionCode)) {
            if (imgcode.equalsIgnoreCase(sessionCode)) {
            	process.setRes(SystemCode.SUCCESS);
            	process.setMsg("验证成功！");
            	return process;
            } 
        }
        process.setMsg("验证失败！");
		return process;
		
	}
	

}
