/*******************************************************************************
 * Copyright (c) 2005, 2014 zzy.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.zzy.brd.util.kaptcha;

import org.apache.shiro.authc.AuthenticationException;

/**
 * 
 */
public class CaptchaException extends AuthenticationException {
	private static final long serialVersionUID = 1L;
	public CaptchaException() {
		super();
	}
	public CaptchaException(String message, Throwable cause) {
		super(message, cause);		
	}
	public CaptchaException(String message) {
		super(message);
	}	
	public CaptchaException(Throwable cause) {
		super(cause);
	}
	
}
