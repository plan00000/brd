/*******************************************************************************
 * Copyright (c) 2005, 2014 zzy.cn
 *
 * 
 *******************************************************************************/
package com.zzy.brd.constant;

import org.apache.shiro.SecurityUtils;

import com.zzy.brd.entity.User;
import com.zzy.brd.shiro.principal.ShiroUser;

/**
 * @author huangjinbing
 * 2015年10月27日
 */
public class GlobalData {
	/**
	 * 取出Shiro中的当前用户Id.
	 */
	public static ShiroUser getCurrentUser() {
		ShiroUser user = null;
		if (SecurityUtils.getSubject() != null
				&& SecurityUtils.getSubject().getPrincipal() instanceof ShiroUser) {
			user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		}
		return user;
	}
	
	/**
	 * 获取当前用户
	 * @return
	 */
	public static User getCurrentLoginUser() {
		ShiroUser shiroUser = getCurrentUser();
		return new User(shiroUser.getId());
	}
}
