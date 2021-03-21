/*******************************************************************************
 * Copyright (c) 2005, 2014 zzy.cn
 *
 * 
 *******************************************************************************/
package com.zzy.brd.mobile.rest.common;

/**
 * 
 * @author tian 2015年1月22日
 */
public class IDCommonResult extends CommonResult {

	private long id;

	/**
	 * @param resultCode
	 */
	protected IDCommonResult(long id, int resultCode) {
		super(resultCode);
		this.id = id;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

}
