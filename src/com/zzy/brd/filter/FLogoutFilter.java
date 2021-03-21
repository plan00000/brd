/*******************************************************************************
 * Copyright (c) 2005, 2014 zzy.cn
 *
 * 
 *******************************************************************************/
package com.zzy.brd.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.util.WebUtils;

/**
 * 自定义退出过滤器
 * @author lxm 2015年2月12日
 */
public class FLogoutFilter extends LogoutFilter {
	@Override
	protected void issueRedirect(ServletRequest request, ServletResponse response, String redirectUrl) throws Exception {
		String forceLogout = request.getParameter("forceLogout");
		String usertype = request.getParameter("usertype");
		if (forceLogout != null && forceLogout.trim().length() != 0) 
			redirectUrl = redirectUrl + (redirectUrl.contains("?") ? "&" : "?") + "forceLogout="+forceLogout;
		else if (usertype != null && usertype.trim().length() != 0) 
			redirectUrl = redirectUrl + (redirectUrl.contains("?") ? "&" : "?") + "usertype="+usertype;
		WebUtils.issueRedirect(request, response, redirectUrl);
    }
}
