/*******************************************************************************
 * Copyright (c) 2005, 2014 zzy.cn
 *
 * 
 *******************************************************************************/
package com.zzy.brd.filter;

import java.io.IOException;

import javax.servlet.Filter;  
import javax.servlet.FilterChain;  
import javax.servlet.FilterConfig;  
import javax.servlet.ServletException;  
import javax.servlet.ServletRequest;  
import javax.servlet.ServletResponse;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils; 
import org.apache.shiro.subject.Subject;
/**
 * ajax 超时过滤
 * @author lxm 2015年3月16日
 */
public class SessionFilter implements Filter  {

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {
				
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;  
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;  
        //      if (httpServletRequest.getSession().getAttribute("user") == null) {  
        Subject subject = SecurityUtils.getSubject();
        if (!subject.isAuthenticated() &&
        		!subject.isRemembered()) {  
            //判断session里是否有用户信息  
            if (httpServletRequest.getHeader("x-requested-with") != null  
                    && httpServletRequest.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {  
                //如果是ajax请求响应头会有，x-requested-with  
                httpServletResponse.setHeader("sessionstatus", "timeout");//在响应头设置session状态  
                return;  
            }  
  
        }  
  
        chain.doFilter(request, response);
		
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}

}
