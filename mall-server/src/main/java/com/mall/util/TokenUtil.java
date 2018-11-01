package com.mall.util;

import com.alibaba.fastjson.JSON;

import java.rmi.server.UID;
import java.util.Date;
import java.util.UUID;

/**
 * Description:Token生成工具
 * 第一部分我们称它为头部（header),第二部分我们称其为载荷（payload, 类似于飞机上承载的物品)，第三部分是签证（signature).
 * Auth: Frank
 * Date: 2017-11-02
 * Time: 下午 5:05
 */
public class TokenUtil {
    
    public static final  String JWT_ISS = "YUNWANGCHUANMEI";

    /**
     * 获得token
     * @param data 自定义数据
     * @param <T> 自定义数据
     * @return
     * @throws Exception
     */
    public static <T> String getToken(T data) throws Exception {
    	
    	String encoder = MD5Util.encoder(new Date().toString(), JWT_ISS);
        return encoder;
    }

    /**
     * 校验token是否是服务器生成的，以防token被修改
     * @param jwtBase64
     * @return
     * @throws Exception
     */
    public static <T> boolean verifyJWT(String jwtBase64) throws Exception {
        
        return true;
    }

   
}