/*******************************************************************************
 * Copyright (c) 2005, 2014 zzy.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.zzy.brd.mobile.rest.common;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 
 * @author tian 2014年12月31日
 */
public class CommonResult {
	private int resultCode;
	private String msg;

	/**
	 * @param resultCode
	 * @param msg
	 */
	protected CommonResult(int resultCode) {
		super();
		this.resultCode = resultCode;
		msg = ResultCode.getResultMessage(resultCode);
	}

	public static CommonResult createCommonResult(int resultCode) {
		return new CommonResult(resultCode);
	}
	
	public CommonResult(int resultCode, String msg) {
		super();
		this.resultCode = resultCode;
		this.msg = msg;
	}

	/**
	 * @return the resultCode
	 */
	public int getResultCode() {
		return resultCode;
	}

	/**
	 * @param resultCode
	 *            the resultCode to set
	 */
	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}

	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * @param msg
	 *            the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		JSONObject obj = new JSONObject();
		try {
			obj.put("resultCode", resultCode);
			obj.put("msg", msg);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return obj.toString();
	}

}
