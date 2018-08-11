package com.mall.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.mall.dao.sequence.Sequence;
import com.mall.entity.login.User;

/**
 * 订单处理工具类
 * @author Zhang
 *
 */
public class ProcessOrderUtil {
	
	
	/**
	 * 生成订单编号
	 * 规则：下单渠道+业务类型+月日+时间戳后5位+用户ID后四位
	 * @param dev
	 * @param business
	 * @param user
	 * @return
	 */
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
	
	
	/**
	 * 生成发货单号
	 * 规则
	 * 年月日+4位时间戳+两位循环码+4位序列
	 * @param sequence
	 * @param orderNumber
	 * @return
	 */
	public static String processDeliveryNumber(Sequence sequence) {
		//年月日+4位时间戳+两位循环码+4位序列
		Date date = DateFormatUtil.getDate();
		long time = date.getTime()/1000;
		String unix = Long.toString(time).substring(4);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String format = sdf.format(date);
		String bout = String.format("%2d", sequence.getBout()).replace(" ", "0");  
		String val = String.format("%4d", sequence.getCurrent_val()).replace(" ", "0");  
		StringBuffer deliveryNumber = new StringBuffer();
		deliveryNumber.append(format);
		deliveryNumber.append(unix);
		deliveryNumber.append(bout);
		deliveryNumber.append(val);
		
		return deliveryNumber.toString();
		
	}
	
	/**
	 * 生成售后服务申请单单号
	 * @param sequence
	 * @param order_number
	 * @return
	 */
	public static String processOrderServiceNumber(int sequence,String order_number) {
		StringBuffer sb =new StringBuffer();
		sb.append(order_number);
		String val = String.format("%2d", sequence).replace(" ", "0");  
		sb.append(val);
		return sb.toString();
		
	}
//	public static void main(String[] args) {
//		String str = String.format("%4d", 12).replace(" ", "0");  
//		System.out.println(str);
//		
//
//	}

}
