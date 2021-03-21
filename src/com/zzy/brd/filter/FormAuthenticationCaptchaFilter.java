/*******************************************************************************
 * Copyright (c) 2005, 2014 zzy.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.zzy.brd.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import com.zzy.brd.shiro.token.CaptchaUsernamePasswordToken;


/**
 * 
 * @author lxm 2014-12-21
 */
public class FormAuthenticationCaptchaFilter extends FormAuthenticationFilter {
	public static final String DEFAULT_CAPTCHA_PARAM = "captcha";
	private String captchaParam = DEFAULT_CAPTCHA_PARAM;
	public static final String DEFAULT_TYPE_PARAM = "type";
	private String typeParam = DEFAULT_TYPE_PARAM;
	public String getCaptchaParam() {
		return captchaParam;
	}	
	public String getTypePararm(){
		return typeParam;
	}
	protected String getCaptcha(ServletRequest request) {
		return WebUtils.getCleanParam(request, getCaptchaParam());
	}
	protected int getType(ServletRequest request) {
		String str = WebUtils.getCleanParam(request, getTypePararm());
		if(null==str){
			return 1;
		}else{
			return Integer.valueOf(str);
		}
	}
	protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
		String username = getUsername(request);
		String password = getPassword(request);
		String captcha = getCaptcha(request);
		boolean rememberMe = isRememberMe(request);
		String host = getHost(request);
		int type = getType(request);
		return new CaptchaUsernamePasswordToken(username,password.toCharArray(), rememberMe, host, captcha, type);
	}
	
	
}
