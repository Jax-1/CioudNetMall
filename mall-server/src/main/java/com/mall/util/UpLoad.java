package com.mall.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.mall.entity.cms.FilePath;
import com.mall.message.SystemCode;
import com.mall.service.cms.FilePathService;
import com.mall.service.sys.CacheService;

public class UpLoad {
	private static final Logger logger =LoggerFactory.getLogger(UpLoad.class);
	/**
	 * 
	 * @param list
	 * @param cacheService
	 * @param filePathService
	 * @return
	 */
	public static List<Map<String, String>> upLoadFies(List<MultipartFile> list,Map<String, String> cache,FilePathService filePathService) {
		List<Map<String, String>> res =new ArrayList<Map<String, String>>();
		for(MultipartFile file:list) {
			 Map<String, String> uploadeFile = UpLoad.uploadeFile(file, cache, filePathService);
			 res.add(uploadeFile);
		}
		return res;
	}
	/**
	 * 获取请求中所有文件
	 * @param request
	 * @return
	 */
	public static List<MultipartFile> getMultipartFiles( HttpServletRequest request){
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
	                                	
	                                    files.add(file1);  
	                                }  
	                            }  
	                        }  
	                    }  
	                }  
	            }  
	        } catch (Exception ex) {  
	        }
		return files;
	}
	
	/**
	 * 保存文件，并存储文件信息
	 * 保存失败返回null,自行判断
	 * @param file
	 * @param cache  文件服务器信息
	 * @param filePathService
	 * @return
	 */
    public static  Map<String,String> uploadeFile( MultipartFile file,Map<String, String> cache,FilePathService filePathService){
    	
        if(file==null||file.isEmpty()){
        	logger.debug("获取文件信息为空！");
            return null;
        }
        String fileName = file.getOriginalFilename();
        int size = (int) file.getSize();
        System.out.println(fileName + "-->" + size);
        //文件服务器路径
        
        
        Calendar now = Calendar.getInstance();  
        String day=now.get(Calendar.YEAR)+"-"+(now.get(Calendar.MONTH)+1)+"-"+now.get(Calendar.DAY_OF_MONTH);
        //文件存储路径：文件服务器绝对路径+文件存储文件夹+日期
        String path = cache.get(SystemCode.FILE_SERVICE_PATH)+"/"+cache.get(SystemCode.FILE_SERVICE_FILES_PATH)+"/"+day;
        String url=cache.get(SystemCode.FILE_SERVICE_URL);
		String port=cache.get(SystemCode.FILE_SERVICE_PORT);
		String filePaths=cache.get(SystemCode.FILE_SERVICE_FILES_PATH);
		String fileUrlPrefix=url+":"+port+"/"+filePaths;
        String serviceFileName=UUIDUtil.getUUID();
        File dest = new File(path + "/" + serviceFileName+"."+fileName.substring(fileName.lastIndexOf(".") + 1));
        logger.info("保存文件："+path + "/" + serviceFileName+"."+fileName.substring(fileName.lastIndexOf(".") + 1));
        
        try {
        	Map<String ,String> map =new HashMap<String ,String>();
        	if("FTP".equals(cache.get(SystemCode.FILE_SERVICE_CON))) {
        		//通过FTP上传
        		SftpUtil sf = new SftpUtil(); 
        		String host=cache.get(SystemCode.FILE_SFTP_SERVICE_HOST);
        		int sftpport=Integer.parseInt(cache.get(SystemCode.FILE_SFTP_SERVICE_PORT));
        		String username=cache.get(SystemCode.FILE_SFTP_USERNAME);
        		String password=cache.get(SystemCode.FILE_SFTP_PASSWORD);
        		try {
					ChannelSftp sftp = sf.connect(host, sftpport, username, password);
					logger.info("PATH:"+path+"  ,"+serviceFileName+"."+fileName.substring(fileName.lastIndexOf(".") + 1));
					sf.upload(path, file.getInputStream(),serviceFileName+"."+fileName.substring(fileName.lastIndexOf(".") + 1), sftp);
				} catch (NullPointerException e) {
					logger.error("文件服务器连接失败！");
					e.printStackTrace();
					return null;
				} catch (JSchException e) {
					logger.error("文件服务器连接失败！");
					e.printStackTrace();
					return null;
				}  catch (Exception e) {
					logger.error("文件保存失败！");
					e.printStackTrace();
					return null;
				}
        		
        	}else {
        		if(!dest.getParentFile().exists()){ //判断文件父目录是否存在
                    dest.getParentFile().mkdir();
                }
        		file.transferTo(dest); //保存文件
        	}
            
            //文件类型
            String fileType=fileName.substring(fileName.lastIndexOf(".") + 1);
            FilePath filePath = new FilePath(serviceFileName,"",day,serviceFileName,
            		fileName,fileType,Long.toString(file.getSize()),
            		SystemCode.STATUS_Y,"",DateFormatUtil.getDate());
            logger.info("保存文件信息成功：ID="+serviceFileName);
            filePathService.insert(filePath);
            map.put("id", serviceFileName);
            map.put("path", day+"/"+serviceFileName+"."+fileName.substring(fileName.lastIndexOf(".") + 1));
            map.put("host", fileUrlPrefix);
            return map;
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }
}
