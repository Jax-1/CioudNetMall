package com.mall.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.mall.entity.login.User;

/**
 * 订单处理工具类
 * @author Zhang
 *
 */
public class ProcessOrderUtil {
	
	public static String processOrderNumber(String dev,String business,User user) {
		//订单编号=下单渠道+业务类型+月日+时间戳后5位+用户ID后四位
		Date date = DateFormatUtil.getDate();
		long time = date.getTime()/1000;
		String unix = Long.toString(time).substring(5);
		String userId = user.getUser_name().substring(7);
		String[] strNow = DateFormatUtil.getStringDateShort().split("-");
		
		StringBuffer orderNumber = new StringBuffer();
		orderNumber.append(dev);
		orderNumber.append(business);
		orderNumber.append(strNow[1]);
		orderNumber.append(strNow[2]);
		orderNumber.append(unix);
		orderNumber.append(userId);
		return orderNumber.toString();
		
	}
//	public static void main(String[] args) {
//		User u =new User();
//		u.setUser_name("13260631321");
//		String number = ProcessOrderUtil.processOrderNumber("1", "0", u);
//		System.out.println(number);
//		
//
//	}

}
