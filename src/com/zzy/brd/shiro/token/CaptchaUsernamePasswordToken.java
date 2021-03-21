/*******************************************************************************
 * Copyright (c) 2005, 2014 zzy.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.zzy.brd.shiro.token;

import org.apache.shiro.authc.UsernamePasswordToken;

import com.zzy.brd.entity.User.UserType;

/**
 * 自定义用户登录权限令牌
 * 
 * @author lxm 2014-12-21
 */
public class CaptchaUsernamePasswordToken extends UsernamePasswordToken {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1012514998904009525L;
	private String captcha; // 验证码
	private UserType type;// 类型

	public CaptchaUsernamePasswordToken(String username, char[] password,
			boolean rememberMe, String host, String captcha, int type) {
		super(username, password, rememberMe, host);
		this.captcha = captcha;
		this.setType(UserType.values()[type]);
	}

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	public CaptchaUsernamePasswordToken() {
		super();
	}

	public UserType getType() {
		return type;
	}

	public void setType(UserType type) {
		this.type = type;
	}
}
