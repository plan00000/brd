package com.zzy.brd.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zzy.brd.constant.Constant;

/**
 * 强制退出拦截器
 * 
 * @author lxm 2015年2月11日
 */
public class ForceLogoutFilter extends AccessControlFilter {
	private static Logger logger = LoggerFactory.getLogger(AccessControlFilter.class);
	private String loginUrl = "";
	@SuppressWarnings("unused")
	private void writeCookie(String name, String value,
			HttpServletResponse response) {
		Cookie cookie = new Cookie(name, value);
		cookie.setPath("/");
		response.addCookie(cookie);
	}

	/***
	 * 判断是否可以访问
	 */
	protected boolean isAccessAllowed(ServletRequest request,
			ServletResponse response, Object mappedValue) throws Exception {
		Subject subject = getSubject(request, response);
		Session session = subject.getSession();
		if (session == null){
			//不存在session,则可以访问
			return true;
		}
		Boolean isForceLogOut = (Boolean) session.getAttribute(Constant.SESSION_FORCE_LOGOUT_KEY);
		if (isForceLogOut != null && isForceLogOut == true){
			//被强制退出
			return false;
		}
		return true;
	}

	/***
	 * 不允许访问时候调用
	 */
	protected boolean onAccessDenied(ServletRequest request,
			ServletResponse response) throws Exception {
		getSubject(request, response).logout();// 强制退出
		WebUtils.issueRedirect(request, response, loginUrl);
		return false;
		/*String loginUrl = "/user/login";
		getSubject(request, response).logout();// 强制退出
		WebUtils.issueRedirect(request, response, loginUrl);
		return false;*/
	}

	public String getLoginUrl() {
		return loginUrl;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

}
