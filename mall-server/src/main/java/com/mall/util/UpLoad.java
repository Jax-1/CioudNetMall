package com.mall.util;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Map;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.mall.entity.cms.FilePath;
import com.mall.message.SystemCode;
import com.mall.service.cms.FilePathService;
import com.mall.service.sys.CacheService;

public class UpLoad {
	private static final Logger logger =LoggerFactory.getLogger(UpLoad.class);
	
	/**
	 * 保存文件，并存储文件信息
	 * @param file
	 * @param cacheService
	 * @param filePathService
	 * @return
	 */
    public static  String fileUpload( MultipartFile file,CacheService cacheService,FilePathService filePathService){
    	
        if(file==null||file.isEmpty()){
        	logger.debug("获取文件信息为空！");
            return "false";
        }
        String fileName = file.getOriginalFilename();
        int size = (int) file.getSize();
        System.out.println(fileName + "-->" + size);
        //文件服务器路径
        
        Map<String, String> cache = cacheService.getCache(SystemCode.FILE_SERVICE);
        Calendar now = Calendar.getInstance();  
        String day=now.get(Calendar.YEAR)+"-"+(now.get(Calendar.MONTH)+1)+"-"+now.get(Calendar.DAY_OF_MONTH);
        //文件存储路径：文件服务器绝对路径+文件存储文件夹+日期
        String path = cache.get(SystemCode.FILE_SERVICE_PATH)+"/"+cache.get(SystemCode.FILE_SERVICE_FILES_PATH)+"/"+day;
        
        String serviceFileName=UUIDUtil.getUUID();
        File dest = new File(path + "/" + serviceFileName+"."+fileName.substring(fileName.lastIndexOf(".") + 1));
        logger.info("保存文件："+path + "/" + serviceFileName+"."+fileName.substring(fileName.lastIndexOf(".") + 1));
        if(!dest.getParentFile().exists()){ //判断文件父目录是否存在
            dest.getParentFile().mkdir();
        }
        try {
            file.transferTo(dest); //保存文件
           
            FilePath filePath = new FilePath(serviceFileName,"",day,serviceFileName,
            		fileName,file.getContentType(),Long.toString(file.getSize()),
            		SystemCode.STATUS_Y,"",DateFormatUtil.getDate());
            logger.info("保存文件信息成功：ID="+serviceFileName);
            filePathService.insert(filePath);
            return day+"/"+serviceFileName+"."+fileName.substring(fileName.lastIndexOf(".") + 1);
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "false";
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "false";
        }
    }
}
