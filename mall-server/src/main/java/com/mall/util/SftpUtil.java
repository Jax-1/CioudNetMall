package com.mall.util;

import java.io.File;  
import java.io.FileInputStream;  
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;  
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.jcraft.jsch.Channel;  
import com.jcraft.jsch.ChannelSftp;  
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;  
import com.jcraft.jsch.SftpException;
import com.mall.entity.cms.FilePath;
import com.mall.message.SystemCode;
import com.mall.service.cms.FilePathService;  
  
public class SftpUtil {  
	private static final Logger logger =LoggerFactory.getLogger(SftpUtil.class);
	
    /**
     *  
     * @param host 主机名
     * @param port
     * @param username
     * @param password
     * @return
     * @throws JSchException 
     */
    public ChannelSftp connect(String host, int port, String username,  
            String password) throws NullPointerException, JSchException{  
        ChannelSftp sftp = null;  
            JSch jsch = new JSch();  
            jsch.getSession(username, host, port);  
            Session sshSession = jsch.getSession(username, host, port);  
            sshSession.setPassword(password);  
            Properties sshConfig = new Properties();  
            sshConfig.put("StrictHostKeyChecking", "no");  
            sshSession.setConfig(sshConfig);  
            sshSession.connect();  
            Channel channel = sshSession.openChannel("sftp");  
            channel.connect();  
            sftp = (ChannelSftp) channel;  
        return sftp;  
    }  
  
    /**
     * Sftp上传文件
     * @param directory
     * @param inputStream
     * @param sftp
     * @throws Exception
     */
    public void upload(String directory, InputStream inputStream, String filename,ChannelSftp sftp) throws Exception {  
    	if(!changeDir(directory,sftp)) {
    		System.out.println("创建文件夹！");
			int lastIndexOf = directory.lastIndexOf("/");
    		String filepath = directory.substring(lastIndexOf+1);
    		String path = directory.substring(0, lastIndexOf);
    		System.out.println(path+","+filepath);
    		sftp.cd(path);
    		sftp.mkdir(filepath); 
    	}
        sftp.cd(directory);
        sftp.put(inputStream, filename); 
    }  
    public boolean changeDir(String pathName,ChannelSftp sftp){
    	  if(pathName == null || pathName.trim().equals("")){
    	       logger.debug("invalid pathName");
    	       return false;
           }
    	  try {
    		  sftp.cd(pathName.replaceAll("\\\\", "/"));
    	     logger.debug("directory successfully changed,current dir=" + sftp.pwd());
    	      return true;
    	   } catch (SftpException e) {
    	      logger.error("failed to change directory",e);
    	       return false;
    	   }
     }
     /*downloadFile 下载的文件 
     saveFile 存在本地的路径*/  
    public void download(String directory, String downloadFile,  
            String saveFile, ChannelSftp sftp) throws Exception {  
        try {  
            sftp.cd(directory);  
            File file = new File(saveFile);  
            sftp.get(downloadFile, new FileOutputStream(file));  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
  
    /*directory 要删除文件所在目录 
    deleteFile 要删除的文件*/  
    public void delete(String directory, String deleteFile, ChannelSftp sftp) {  
        try {  
            sftp.cd(directory);  
            sftp.rm(deleteFile);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
  
    //directory 要列出的目录  
    public Vector listFiles(String directory, ChannelSftp sftp)  
            throws SftpException {  
        return sftp.ls(directory);  
    }  
  
//    public static void main(String[] args) {  
//        SftpUtil sf = new SftpUtil();  
//        String host = "47.93.16.119";  
//        int port = 22;  
//        String username = "root";  
//        String password = "zhang032111ZZ";  
//        String directory = "/usr/service/apache-tomcat-8.0.52/webapps/file";  
//        String uploadFile = "D:\\validateCode.jpg";  
//        //String downloadFile = "upload.txt";  
//        //String saveFile = "D:\\tmp\\save.txt";  
//        //String deleteFile = "delete.txt";  
////        ChannelSftp sftp = sf.connect(host, port, username, password);  
//       // sf.upload(directory, uploadFile, sftp); 
//        
//        
//        
//        //sf.download(directory, downloadFile, saveFile, sftp);  
//        //sf.delete(directory, deleteFile, sftp);  
//        try {  
//            System.out.println("成功");  
//        } catch (Exception e) {  
//            e.printStackTrace();  
//        }  
//    }  
} 
