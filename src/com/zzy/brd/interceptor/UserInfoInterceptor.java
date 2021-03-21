package com.zzy.brd.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.zzy.brd.shiro.principal.ShiroUser;

public class UserInfoInterceptor implements HandlerInterceptor{

	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse arg1,
			Object arg2, ModelAndView modelAndView) throws Exception {
		if(modelAndView != null){
			Subject subject = SecurityUtils.getSubject();
			//如果是通过登录操作进行系统的话，则isAuthenticated为true,isRemembered为false
			//如果是通过rememberMe登录的，则isRemembered为true,isAuthenticated为false
			boolean isAuthenticated = subject.isAuthenticated();
			boolean isRemembered = subject.isRemembered();
			ShiroUser user = (ShiroUser) subject.getPrincipal();
			modelAndView.addObject("a", isAuthenticated);
			modelAndView.addObject("r", isRemembered);
			modelAndView.addObject("user", user);
			if (user != null){
				modelAndView.addObject("u_name", user.getName());
				modelAndView.addObject("u_headUrl", user.getHeadUrl());
				modelAndView.addObject("u_position", user.getPosition());
			}
		}
	}

	@Override
	public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2) throws Exception {
		// TODO Auto-generated method stub
		return true;
	}

}
