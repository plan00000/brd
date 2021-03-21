/*******************************************************************************
 * Copyright (c) 2005, 2014 zzy.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.zzy.brd.mobile.rest.common;

import javax.annotation.PostConstruct;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springside.modules.utils.Encodes;

import com.zzy.brd.algorithm.encrypt.shiro.SHA1Encrypt;
import com.zzy.brd.entity.User;
import com.zzy.brd.mobile.rest.filter.ApiAuthenticationFilter;
import com.zzy.brd.service.UserService;

/**
 * 
 * @author tian 2015年1月6日
 */
public class ApiShiroDbRealm extends AuthorizingRealm {

	protected UserService userService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.shiro.realm.AuthorizingRealm#doGetAuthorizationInfo(org.apache
	 * .shiro.subject.PrincipalCollection)
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
		return new SimpleAuthorizationInfo();
	}

	/**
	 * @param userService
	 *            the userService to set
	 */
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.shiro.realm.AuthenticatingRealm#doGetAuthenticationInfo(org
	 * .apache.shiro.authc.AuthenticationToken)
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken atoken) throws AuthenticationException {
		ZzyCheckToken token = (ZzyCheckToken) atoken;
		// TODO 根据用户名 数据库查找 用户信息
		User user = userService.findByMobileno(token.getUsername());
		if (user != null) {
			// 判断用户是否启用状态
			if (user.getState() == User.State.OFF) {
				throw 
				     new AuthenticationException(String.valueOf(ResultCode.LOGIN_ACCOUNT_FORBIDDEN));
			}
			//状态为删除
			if (user.getState() == User.State.DEL) {
				throw new AuthenticationException(String.valueOf(ResultCode.LOGIN_TYPE_PASSWORD_ERROR));
			}
			token.setUserId(user.getId());
			ApiAuthenticationFilter.putToken(token);
			byte[] salt = Encodes.decodeHex(user.getSalt());
			return new SimpleAuthenticationInfo(user.getUsername(),
					user.getPassword(), ByteSource.Util.bytes(salt), getName());
		} else {// 用户不存在
			throw new AuthenticationException(String.valueOf(ResultCode.LOGIN_TYPE_PASSWORD_ERROR));
		}
	}

	/**
	 * 设定Password校验的Hash算法与迭代次数.
	 */
	@PostConstruct
	public void initCredentialsMatcher() {
		SHA1Encrypt encrypt = SHA1Encrypt.getInstance();
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(encrypt.getHashAlgorithName());
		matcher.setHashIterations(encrypt.getHashIterations());
		setCredentialsMatcher(matcher);
	}

}
