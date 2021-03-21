/*******************************************************************************
 * Copyright (c) 2005, 2014 zzy.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.zzy.brd.mobile.rest.common;

import org.apache.shiro.authc.UsernamePasswordToken;

import com.zzy.util.MD5;

/**
 * 
 * @author tian 2015年1月7日
 */
public class ZzyCheckToken extends UsernamePasswordToken {

	private static final String PRIMARY_KEY = "testPrimaryKey";

	/**
	 * 
	 */
	private static final long serialVersionUID = -2814805370060666087L;

	/** 设备ID */
	private String imei;

	/** 时间 */
	private long time;

	/** 用户ID */
	private long userId;

	/** 用户IP */
	private String ip = "";

	/**
	 * @param username
	 * @param password
	 */
	public ZzyCheckToken(String username, String password, String host,
			String imei) {
		super(username, password, host);
		this.imei = imei;
		time = System.currentTimeMillis();
	}

	/**
	 * @return the imei
	 */
	public String getImei() {
		return imei;
	}

	public String getAppToken() {
		return MD5.hex_md5(PRIMARY_KEY + time).toLowerCase();
	}

	public boolean checkToken(String token) {
		String md5 = MD5.hex_md5(imei + ":" + userId + ":" + getAppToken())
				.toLowerCase();
		boolean result = md5
				.equals(token.toLowerCase().trim().substring(0, 32));
		return result;
	}


	public String getKey() {
		return generateKey(getUserId());
	}

	public static String generateKey(long userid) {
		return Long.toString(userid);
	}

	/**
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * @param ip
	 *            the ip to set
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * @return the userId
	 */
	public long getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(long userId) {
		this.userId = userId;
	}

	// /*
	// * (non-Javadoc)
	// *
	// * @see org.apache.shiro.authc.AuthenticationToken#getCredentials()
	// */
	// public Object getCredentials() {
	// return getPassword();
	// }

}
