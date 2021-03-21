/*******************************************************************************
 * Copyright (c) 2005, 2014 zzy.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.zzy.brd.mobile.rest.filter;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.util.WebUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zzy.brd.entity.User;
import com.zzy.brd.mobile.rest.common.ResultCode;
import com.zzy.brd.mobile.rest.common.ZzyCheckToken;
import com.zzy.brd.service.UserService;


/**
 *  api filter
 */
public class ApiAuthenticationFilter extends AuthenticatingFilter {

	private static Logger logger = LoggerFactory
			.getLogger(ApiAuthenticationFilter.class);

	protected UserService userService;

	/**
	 * @return the userService
	 */
	public UserService getUserService() {
		return userService;
	}

	/**
	 * @param userService
	 *            the userService to set
	 */
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	protected static final String HEADER_AUTHORIZATION = "Authorization";

	public static Map<String, ZzyCheckToken> map = new Hashtable<String, ZzyCheckToken>();

	protected static String getAuthzHeader(ServletRequest request) {
		HttpServletRequest httpRequest = WebUtils.toHttp(request);
		return httpRequest.getHeader(HEADER_AUTHORIZATION);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.shiro.web.filter.authc.AuthenticatingFilter#createToken(javax
	 * .servlet.ServletRequest, javax.servlet.ServletResponse)
	 */
	protected AuthenticationToken createToken(ServletRequest request,
			ServletResponse response) {
		String authorizationHeader = getAuthzHeader(request);
		if (authorizationHeader == null) {
			// return new ZzyCheckToken("", "", "", "");
		} else {
			// 返回新生成的TOKEN
		}
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter#
	 * onAccessDenied(javax.servlet.ServletRequest,
	 * javax.servlet.ServletResponse)
	 */
	@Override
	protected boolean onAccessDenied(ServletRequest request,
			ServletResponse response) throws Exception {
		boolean loggedIn = false; // false by default or we wouldn't be in this
		// method
		if (isLoginAttempt(request, response)) {
			// 如果头部没传令牌，则需要登录
			loggedIn = executeLogin(request, response);
		} else {
			String authzHeader = getAuthzHeader(request);

			String[] strs = getImeiAndToken(authzHeader);
			ZzyCheckToken token = map.get(strs[0]);
			if (token == null) {
				sendChallenge(response);
				return false;
			}else if (!token.checkToken(strs[1])) {
//				if (token != null)
//					map.remove(token.getKey());
				sendChallengeUserLoginConflict(response);
				return false;
			} else {
				// 登录成功
				if (!token.getIp().equals(request.getRemoteAddr())) {
					// ip变化
					token.setIp(request.getRemoteAddr());
				}
				return true;
			}

		}
		if (!loggedIn) {// 登录账号或密码错误
			String username = request.getParameter("account");
			if (username == null || username == "") {
				sendLoginFailureChallenge(response);
			} else {
				User user = userService.findByMobileno(username);
				if(user != null && user.getState() != User.State.ON){
					
				}else {
					sendLoginFailureChallenge(response);
				}
			}
		} else {
			logger.info("login success...");
			HttpServletRequest httpRequest = WebUtils.toHttp(request);
		}
		return loggedIn;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.shiro.web.filter.authc.AuthenticatingFilter#isAccessAllowed
	 * (javax.servlet.ServletRequest, javax.servlet.ServletResponse,
	 * java.lang.Object)
	 */
	@Override
	protected boolean isAccessAllowed(ServletRequest request,
			ServletResponse response, Object mappedValue) {
		// Subject subject = getSubject(request, response);
		// boolean result = subject.isAuthenticated();
		return false;
	}

	public static boolean isLoginAttempt(ServletRequest request,
			ServletResponse response) {
		String authzHeader = getAuthzHeader(request);
		return authzHeader == null;
	}

	private void sendChallenge(ServletResponse response) {
		JSONObject obj = new JSONObject();
		try {
			obj.put("resultCode", ResultCode.COMMON_CHECK_TOKEN_FAIL);
			obj.put("msg", ResultCode
					.getResultMessage(ResultCode.COMMON_CHECK_TOKEN_FAIL));
			response.getWriter().println(obj.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		// HttpServletResponse httpResponse = WebUtils.toHttp(response);
		// httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	}

	private void sendLoginFailureChallenge(ServletResponse response) {
		JSONObject obj = new JSONObject();
		try {
			obj.put("resultCode", ResultCode.COMMON_LOGIN_PWD_ERROR);
			obj.put("msg", ResultCode
					.getResultMessage(ResultCode.COMMON_LOGIN_PWD_ERROR));
			response.getWriter().println(obj.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		// HttpServletResponse httpResponse = WebUtils.toHttp(response);
		// httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	}

	private void sendLoginFailureMessage(AuthenticationException ex,
			ServletResponse response) {
		JSONObject obj = new JSONObject();
		try {
			int code = Integer.parseInt(ex.getMessage());
			obj.put("resultCode", code);
			obj.put("msg", ResultCode.getResultMessage(code));
			response.getWriter().println(obj.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		// HttpServletResponse httpResponse = WebUtils.toHttp(response);
		// httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.shiro.web.filter.authc.AuthenticatingFilter#onLoginFailure
	 * (org.apache.shiro.authc.AuthenticationToken,
	 * org.apache.shiro.authc.AuthenticationException,
	 * javax.servlet.ServletRequest, javax.servlet.ServletResponse)
	 */
	@Override
	protected boolean onLoginFailure(AuthenticationToken token,
			AuthenticationException e, ServletRequest request,
			ServletResponse response) {
		logger.info("login failure..." + e.getLocalizedMessage());
		if (e.getLocalizedMessage().equals(
				String.valueOf(ResultCode.LOGIN_ACCOUNT_FORBIDDEN))) {
			sendLoginFailureMessage(e, response);
		}
		if (e.getLocalizedMessage().equals(
				String.valueOf(ResultCode.USER_NOT_EXIST))) {
			sendLoginFailureMessage(e, response);
		}
		if (e.getLocalizedMessage().equals(
				String.valueOf(ResultCode.LOGIN_TYPE_PASSWORD_ERROR))){
			sendLoginFailureMessage(e, response);
		}
		return super.onLoginFailure(token, e, request, response);
	}

	private void sendChallengeUserLoginConflict(ServletResponse response) {
		JSONObject obj = new JSONObject();
		try {
			obj.put("resultCode", ResultCode.LOGIN_USER_CONFLICT);
			obj.put("msg", ResultCode
					.getResultMessage(ResultCode.LOGIN_USER_CONFLICT));
			response.getWriter().println(obj.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		//HttpServletResponse httpResponse = WebUtils.toHttp(response);
		//httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.shiro.web.filter.authc.AuthenticatingFilter#onLoginSuccess
	 * (org.apache.shiro.authc.AuthenticationToken,
	 * org.apache.shiro.subject.Subject, javax.servlet.ServletRequest,
	 * javax.servlet.ServletResponse)
	 */
	@Override
	protected boolean onLoginSuccess(AuthenticationToken token,
			Subject subject, ServletRequest request, ServletResponse response)
			throws Exception {
		((ZzyCheckToken) token).setIp(request.getRemoteAddr());
		return super.onLoginSuccess(token, subject, request, response);
	}

	public static String[] getImeiAndToken(String encoded) {
		String decoded = Base64.decodeToString(encoded);
		return decoded.split(":", 2);
	}

	public static String getToken(String key) {
		ZzyCheckToken token = map.get(key);
		if (token != null) {
			return token.getAppToken();
		}
		return "";
	}

	public static ZzyCheckToken getZzyToken(String key) {
		ZzyCheckToken token = map.get(key);
		if (token != null) {
			return token;
		}
		return null;
	}
	
	public static ZzyCheckToken getZzyToken(HttpServletRequest request){
		String authzInfo = getAtuhzInfo(request);
		if (StringUtils.isNotBlank(authzInfo)){
			String[] strs = ApiAuthenticationFilter.getImeiAndToken(authzInfo);
			ZzyCheckToken token = ApiAuthenticationFilter.map.get(strs[0]);
			return token;
		}else{
			return null;
		}
	}

	public static void putToken(ZzyCheckToken token) {
		map.put(token.getKey(), token);
	}

	public static void removeToken(long userid) {
		map.remove(ZzyCheckToken.generateKey(userid));
	}

	protected static String getAuthzParms(ServletRequest request) {
		return request.getParameter(HEADER_AUTHORIZATION);
	}
	
	public static String getAtuhzInfo(ServletRequest request){
		String authzInfo = getAuthzParms(request);
		if (StringUtils.isBlank(authzInfo)){
			authzInfo = getAuthzHeader(request);
		}
		return authzInfo;
	}


}
