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
}
