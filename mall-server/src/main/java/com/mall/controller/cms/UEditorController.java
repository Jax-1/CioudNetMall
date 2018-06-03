package com.mall.controller.cms;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;  
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.mall.controller.AbstractController;
import com.mall.message.SystemCode;
import com.mall.service.cms.FilePathService;
import com.mall.service.sys.CacheService;
import com.mall.util.UpLoad;

 
/**
 * Ueditor编辑器
 * @author Jang
 *
 */
@Controller  
@RequestMapping("/ueditor")  	
public class UEditorController extends AbstractController{ 
	@Resource
	private CacheService cacheService;
	
	@Resource
	private FilePathService filePathService;
	
	
	@RequestMapping("/ue")
	public String toIndex() {
		return "index";
	}
    
	@RequestMapping("/config")
	@ResponseBody
	private String config(HttpServletRequest request,String action) {
		//上传图片、视频、文件执行
		if("uploadimage".equals(action)||"uploadvideo".equals(action)||"uploadfile".equals(action)) {
			 List<MultipartFile> files = new ArrayList<MultipartFile>(); 
			 String path=null;
		        try {  
		            CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(  
		                    request.getSession().getServletContext());  
		            if (request instanceof MultipartHttpServletRequest) {  
		                // 将request变成多部分request  
		                MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;  
		                Iterator<String> iter = multiRequest.getFileNames();  
		                // 检查form中是否有enctype="multipart/form-data"  
		                if (multipartResolver.isMultipart(request) && iter.hasNext()) {  
		                    // 获取multiRequest 中所有的文件名  
		                    while (iter.hasNext()) {  
		                        // 适配名字重复的文件  
		                        List<MultipartFile> fileRows = multiRequest  
		                                .getFiles(iter.next().toString());  
		                        if (fileRows != null && fileRows.size() != 0) {  
		                            for (MultipartFile file1 : fileRows) {  
		                                if (file1 != null && !file1.isEmpty()) { 
		                                	path=UpLoad.fileUpload(file1,cacheService,filePathService);
		                                    files.add(file1);  
		                                }  
		                            }  
		                        }  
		                    }  
		                }  
		            }  
		        } catch (Exception ex) {  
		        }
		    //上传结果
		    String state=!"false".equals(path)?"SUCCESS":"";
			String config =
	                "{\n" +
	                "            \"state\": \""+state+"\",\n" +
	                "                \"url\": \"/"+path+"\",\n" +
	                "                \"title\": \"path\",\n" +
	                "                \"original\": \"path\"\n" +
	                "        }";

	    	return config;
		}
		Map<String, String> cache = cacheService.getCache(SystemCode.FILE_SERVICE);
		String url=cache.get(SystemCode.FILE_SERVICE_URL);
		String port=cache.get(SystemCode.FILE_SERVICE_PORT);
		String filePath=cache.get(SystemCode.FILE_SERVICE_FILES_PATH);
		String fileUrlPrefix=url+":"+port+"/"+filePath;
		logger.info("获取文件访问前缀："+fileUrlPrefix);
		String s="/* 前后端通信相关的配置,注释只允许使用多行方式 */\r\n" + 
				"{\r\n" + 
				"    /* 上传图片配置项 */\r\n" + 
				"    \"imageActionName\": \"uploadimage\", /* 执行上传图片的action名称 */\r\n" + 
				"    \"imageFieldName\": \"upfile\", /* 提交的图片表单名称 */\r\n" + 
				"    \"imageMaxSize\": 204800000, /* 上传大小限制，单位B */\r\n" + 
				"    \"imageAllowFiles\": [\".png\", \".jpg\", \".jpeg\", \".gif\", \".bmp\"], /* 上传图片格式显示 */\r\n" + 
				"    \"imageCompressEnable\": true, /* 是否压缩图片,默认是true */\r\n" + 
				"    \"imageCompressBorder\": 1600, /* 图片压缩最长边限制 */\r\n" + 
				"    \"imageInsertAlign\": \"none\", /* 插入的图片浮动方式 */\r\n" + 
				"    \"imageUrlPrefix\": \""+fileUrlPrefix+"\", /* 图片访问路径前缀 */\r\n" + 
				"    \"imagePathFormat\": \"/ueditor/jsp/upload/image/{yyyy}{mm}{dd}/{time}{rand:6}\", /* 上传保存路径,可以自定义保存路径和文件名格式 */\r\n" + 
				"                                /* {filename} 会替换成原文件名,配置这项需要注意中文乱码问题 */\r\n" + 
				"                                /* {rand:6} 会替换成随机数,后面的数字是随机数的位数 */\r\n" + 
				"                                /* {time} 会替换成时间戳 */\r\n" + 
				"                                /* {yyyy} 会替换成四位年份 */\r\n" + 
				"                                /* {yy} 会替换成两位年份 */\r\n" + 
				"                                /* {mm} 会替换成两位月份 */\r\n" + 
				"                                /* {dd} 会替换成两位日期 */\r\n" + 
				"                                /* {hh} 会替换成两位小时 */\r\n" + 
				"                                /* {ii} 会替换成两位分钟 */\r\n" + 
				"                                /* {ss} 会替换成两位秒 */\r\n" + 
				"                                /* 非法字符 \\ : * ? \" < > | */\r\n" + 
				"                                /* 具请体看线上文档: fex.baidu.com/ueditor/#use-format_upload_filename */\r\n" + 
				"\r\n" + 
				"    /* 上传视频配置 */\r\n" + 
				"    \"videoActionName\": \"uploadvideo\", /* 执行上传视频的action名称 */\r\n" + 
				"    \"videoFieldName\": \"upfile\", /* 提交的视频表单名称 */\r\n" + 
				"    \"videoPathFormat\": \"/ueditor/jsp/upload/video/{yyyy}{mm}{dd}/{time}{rand:6}\", /* 上传保存路径,可以自定义保存路径和文件名格式 */\r\n" + 
				"    \"videoUrlPrefix\": \""+fileUrlPrefix+"\", /* 视频访问路径前缀 */\r\n" + 
				"    \"videoMaxSize\": 102400000, /* 上传大小限制，单位B，默认100MB */\r\n" + 
				"    \"videoAllowFiles\": [\r\n" + 
				"        \".flv\", \".swf\", \".mkv\", \".avi\", \".rm\", \".rmvb\", \".mpeg\", \".mpg\",\r\n" + 
				"        \".ogg\", \".ogv\", \".mov\", \".wmv\", \".mp4\", \".webm\", \".mp3\", \".wav\", \".mid\"], /* 上传视频格式显示 */\r\n" + 
				"\r\n" + 
				"    /* 上传文件配置 */\r\n" + 
				"    \"fileActionName\": \"uploadfile\", /* controller里,执行上传视频的action名称 */\r\n" + 
				"    \"fileFieldName\": \"upfile\", /* 提交的文件表单名称 */\r\n" + 
				"    \"filePathFormat\": \"/ueditor/jsp/upload/file/{yyyy}{mm}{dd}/{time}{rand:6}\", /* 上传保存路径,可以自定义保存路径和文件名格式 */\r\n" + 
				"    \"fileUrlPrefix\": \""+fileUrlPrefix+"\", /* 文件访问路径前缀 */\r\n" + 
				"    \"fileMaxSize\": 51200000, /* 上传大小限制，单位B，默认50MB */\r\n" + 
				"    \"fileAllowFiles\": [\r\n" + 
				"        \".png\", \".jpg\", \".jpeg\", \".gif\", \".bmp\",\r\n" + 
				"        \".flv\", \".swf\", \".mkv\", \".avi\", \".rm\", \".rmvb\", \".mpeg\", \".mpg\",\r\n" + 
				"        \".ogg\", \".ogv\", \".mov\", \".wmv\", \".mp4\", \".webm\", \".mp3\", \".wav\", \".mid\",\r\n" + 
				"        \".rar\", \".zip\", \".tar\", \".gz\", \".7z\", \".bz2\", \".cab\", \".iso\",\r\n" + 
				"        \".doc\", \".docx\", \".xls\", \".xlsx\", \".ppt\", \".pptx\", \".pdf\", \".txt\", \".md\", \".xml\"\r\n" + 
				"    ], /* 上传文件格式显示 */\r\n" + 
				"\r\n" + 
				"    /* 列出指定目录下的图片 */\r\n" + 
				"    \"imageManagerActionName\": \"listimage\", /* 执行图片管理的action名称 */\r\n" + 
				"    \"imageManagerListPath\": \"/ueditor/jsp/upload/image/\", /* 指定要列出图片的目录 */\r\n" + 
				"    \"imageManagerListSize\": 20, /* 每次列出文件数量 */\r\n" + 
				"    \"imageManagerUrlPrefix\": \""+fileUrlPrefix+"\", /* 图片访问路径前缀 */\r\n" + 
				"    \"imageManagerInsertAlign\": \"none\", /* 插入的图片浮动方式 */\r\n" + 
				"    \"imageManagerAllowFiles\": [\".png\", \".jpg\", \".jpeg\", \".gif\", \".bmp\"], /* 列出的文件类型 */\r\n" + 
				"\r\n" + 
				"    /* 列出指定目录下的文件 */\r\n" + 
				"    \"fileManagerActionName\": \"listfile\", /* 执行文件管理的action名称 */\r\n" + 
				"    \"fileManagerListPath\": \"/ueditor/jsp/upload/file/\", /* 指定要列出文件的目录 */\r\n" + 
				"    \"fileManagerUrlPrefix\": \""+fileUrlPrefix+"\", /* 文件访问路径前缀 */\r\n" + 
				"    \"fileManagerListSize\": 20, /* 每次列出文件数量 */\r\n" + 
				"    \"fileManagerAllowFiles\": [\r\n" + 
				"        \".png\", \".jpg\", \".jpeg\", \".gif\", \".bmp\",\r\n" + 
				"        \".flv\", \".swf\", \".mkv\", \".avi\", \".rm\", \".rmvb\", \".mpeg\", \".mpg\",\r\n" + 
				"        \".ogg\", \".ogv\", \".mov\", \".wmv\", \".mp4\", \".webm\", \".mp3\", \".wav\", \".mid\",\r\n" + 
				"        \".rar\", \".zip\", \".tar\", \".gz\", \".7z\", \".bz2\", \".cab\", \".iso\",\r\n" + 
				"        \".doc\", \".docx\", \".xls\", \".xlsx\", \".ppt\", \".pptx\", \".pdf\", \".txt\", \".md\", \".xml\"\r\n" + 
				"    ] /* 列出的文件类型 */\r\n" + 
				"\r\n" + 
				"}";
		return s;
	}
}  
