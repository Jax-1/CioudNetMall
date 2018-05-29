package com.mall.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

public class UpLoad {
	 //图片保存路径  
    public static String imgResouse = "D:\\upload\\img\\";  
    /** 
     * 多图上传 
     * 
     * @param files 
     */  
    public static List<String> imgUpLoad(List<MultipartFile> files) {  
        List<String> urls = new ArrayList<>();  
        for (MultipartFile file : files) {  
            String fileName = file.getOriginalFilename();  
            //图片名称格式：uuid+后缀，主要解决文件可能存在的重名覆盖问题  
            fileName = UUID.randomUUID().toString().replaceAll("-", "") + fileName.substring(fileName.lastIndexOf("."));  
            File dest = new File(imgResouse + fileName);  
            // 检测是否存在目录  
            if (!dest.getParentFile().exists()) {  
                dest.getParentFile().mkdirs();  
            }  
        //文件保存  
            try {  
                file.transferTo(dest);  
        //这里我设定url仅保存文件名  
                urls.add(fileName);  
            } catch (IllegalStateException e) {  
                e.printStackTrace();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
        return urls;  
    }  
}
