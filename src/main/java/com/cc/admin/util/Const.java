package com.cc.admin.util;

import org.springframework.context.ApplicationContext;


public class Const {
	public static final String PAGE	= "admin/config/PAGE.txt";				//分页条数配置路径
	public static final String KB12 = "091A380801AA41E4A508D9AC2455AF87";  //上午12节课
	public static final String KB34 = "D6C8D0BA1DEE4D98A1D47765A7CA709F";	//上午34节课
	public static final String KB56 = "F030213D26CE49FF8DA7E98FD22DA75D";	//上午56节课
	public static final String KB78 = "2F5FCDA52E5A4100861BDE31B751C059";	//上午78节课
	public static final String KB910 = "1452977F491543298D0577AF83C56602";	//上午9，10，11节课

	/**
	 * APP Constants
	 */
	//系统用户注册接口_请求协议参数)
	public static final String[] SYSUSER_REGISTERED_PARAM_ARRAY = new String[]{"USERNAME","PASSWORD","NAME","EMAIL","rcode"};
	public static final String[] SYSUSER_REGISTERED_VALUE_ARRAY = new String[]{"用户名","密码","姓名","邮箱","验证码"};
	
	//app根据用户名获取会员信息接口_请求协议中的参数
	public static final String[] APP_GETAPPUSER_PARAM_ARRAY = new String[]{"USERNAME"};
	public static final String[] APP_GETAPPUSER_VALUE_ARRAY = new String[]{"用户名"};
	
}
