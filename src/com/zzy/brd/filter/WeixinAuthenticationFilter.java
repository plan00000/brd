package com.zzy.brd.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import com.zzy.brd.mobile.rest.common.ZzyCheckToken;
//import com.zzy.brd.mobile.rest.filter.ApiAuthenticationFilter;
import com.zzy.brd.service.UserService;

/**
 * weixin filter
 * @author lzh 2016/9/13
 *
 */
/*public class WeixinAuthenticationFilter extends AuthenticatingFilter{
	
	private static Logger logger = LoggerFactory.getLogger(WeixinAuthenticationFilter.class);
	//private String weixinLoginUrl = "/weixin/user/toLogin";
	//private String weixinLoginUrl = "/brd/weixin/user/toLogin";
//	private String weixinLoginUrl = "/brd/wechat/weixinLogin";//微信登录 本地
	private String weixinLoginUrl =  "/wechat/weixinLogin";//微信登录 服务器
	private UserService userService;
	
	@Override
	protected AuthenticationToken createToken(ServletRequest request,
			ServletResponse arg1) throws Exception {
		String username = request.getParameter("account");
		String pwd = request.getParameter("password");
		String imei = request.getParameter("imei");
		if (username == null) {
			username = "";
		}
		if (pwd == null) {
			pwd = "";
		} else {
			pwd = pwd.toLowerCase();
		}
		if (imei == null) {
			imei = System.currentTimeMillis() + "";
		}
		ZzyCheckToken token = new ZzyCheckToken(username, pwd, "", imei);
		return token;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request,
			ServletResponse response)
			throws Exception {
		boolean loggedIn = false;
		HttpServletResponse httpResponse = WebUtils.toHttp(response);
		HttpServletRequest httpRequest = WebUtils.toHttp(request);
		if (ApiAuthenticationFilter.isLoginAttempt(request, response)) {
			// 如果头部没传令牌，则需要登录
			loggedIn = executeLogin(request, response);
		} else {
			String authzPara = ApiAuthenticationFilter.getAtuhzInfo(request);
			String[] strs = ApiAuthenticationFilter.getImeiAndToken(authzPara);
			ZzyCheckToken token = ApiAuthenticationFilter.map.get(strs[0]);
			if (token == null) {
				loggedIn = false;
			} else if (!token.checkToken(strs[1])) {
				loggedIn = false;
			} else {
				// 登录成功
				if (!token.getIp().equals(request.getRemoteAddr())) {
					// ip变化
					token.setIp(request.getRemoteAddr());
				}
			    loggedIn = true;
			}

		}
		if (!loggedIn) {// 登录账号或密码错误
			httpResponse.sendRedirect(weixinLoginUrl);
			return false;
		} else {
			logger.info("weixin login success...");
			return true;
		}
	}
	
	@Override
	protected boolean isAccessAllowed(ServletRequest request,
			ServletResponse response, Object mappedValue) {
		return super.isAccessAllowed(request, response, mappedValue);
	}

	@Override
	protected boolean onLoginSuccess(AuthenticationToken token,
			Subject subject, ServletRequest request, ServletResponse response)
			throws Exception {
		((ZzyCheckToken) token).setIp(request.getRemoteAddr());
		return super.onLoginSuccess(token, subject, request, response);
	}
	public String getWeixinLoginUrl() {
		return weixinLoginUrl;
	}

	public void setWeixinLoginUrl(String weixinLoginUrl) {
		this.weixinLoginUrl = weixinLoginUrl;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

}*/
