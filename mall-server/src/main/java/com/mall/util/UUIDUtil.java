package com.mall.util;

import java.util.UUID;

/*
 * UUID
 */
public class UUIDUtil {
	public static String getUUID(){
        return UUID.randomUUID().toString().replace("-", "");
   }
}
