package com.mall.code;

public class SystemCode {
	//状态：开启
	public final static String STATUS_Y;
	//状态：关闭
	public final static String STATUS_N;
	//状态关闭消息
	public final static String USER_STATUS_N_MSG;
	//登陆成功
	public final static String LOGIN_SUCESS;
	//登陆失败
	public final static String LOGIN_FAILURE;
	//注销成功
	public final static String LOGIN_OUT_SUCESS;
	//用户默认头像
	public final static String USERINFO_HEADIMGURL;
	static {
		STATUS_Y="Y";
		STATUS_N="N";
		USER_STATUS_N_MSG="无效用户！";
		LOGIN_SUCESS="登陆成功！";
		LOGIN_FAILURE="登陆失败！";
		LOGIN_OUT_SUCESS="注销成功！";
		USERINFO_HEADIMGURL="";
	}

}
