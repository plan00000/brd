/*******************************************************************************
 * Copyright (c) 2005, 2014 zzy.cn
 *
 * 
 *******************************************************************************/
package com.zzy.brd.enums;

/**
 * 短信校验码来源
 * 
 * @author Toni 2015年1月28日
 */
public enum SmsAuthcodeSource {
	 //0-会员注册 1-会员修改密码 2-更换手机号 3修改提现密码 4-提现获取验证码
	USER_REG,
	USER_RETPASS,
	USER_CHANGEMOBILENO,
	USER_RETPASS_WITHAW,
	USER_WITHDRAW,
	PC_ADDORDERFORM;
}
