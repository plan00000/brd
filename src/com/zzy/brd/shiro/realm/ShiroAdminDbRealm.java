/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.zzy.brd.shiro.realm;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityNotFoundException;

import org.apache.shiro.SecurityUtils;
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
import com.zzy.brd.constant.Constant;
import com.zzy.brd.constant.GlobalData;
import com.zzy.brd.entity.Role;
import com.zzy.brd.entity.User;
import com.zzy.brd.entity.User.State;
import com.zzy.brd.entity.User.UserType;
import com.zzy.brd.enums.Permission;
import com.zzy.brd.service.UserService;
import com.zzy.brd.shiro.principal.ShiroUser;
import com.zzy.brd.shiro.token.CaptchaUsernamePasswordToken;
import com.zzy.brd.util.kaptcha.CaptchaException;

public class ShiroAdminDbRealm extends AuthorizingRealm {
	private UserService userService;

	/**
	 * 认证回调函数,登录时调用. 抛出异常，返回null，密码验证错误都会返回到loginUrl，可通过session来传递信息
	 * 如果成功的话，会返回到successUrl
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authcToken) throws AuthenticationException {
		CaptchaUsernamePasswordToken token = (CaptchaUsernamePasswordToken) authcToken;
		String username = token.getUsername();
		String captcha = token.getCaptcha();
		// 验证登录信息错误次数，错误则显示验证码
		Integer times = 0;
		Object obj = SecurityUtils.getSubject().getSession().getAttribute(Constant.SYS_ERROR_LOGIN_TIMES);
		if(null!=obj)
			times = (Integer)obj;
		// 验证验证码是否正确
		if(times>=Constant.SYS_ERROR_LOGIN_TIMES_VALUE){
			String code = (String) SecurityUtils.getSubject().getSession().getAttribute(Constant.KAPTCHA_SESSION_KEY_USER_LOGIN);
			if (code == null || captcha == null || !code.equals(captcha)) {
				SecurityUtils.getSubject().getSession().setAttribute("KEY_CAPTCHA_ERROR", "captcha error");
				throw new CaptchaException("catcha error");
			} else {
				SecurityUtils.getSubject().getSession().setAttribute("KEY_CAPTCHA_ERROR", "captcha success");
			}
		}
		// 通过username查询数据库获得User对象
		//User user = getUserService().getUser(username);
		List<User> users = userService.getUsers(username.toLowerCase());
		User user = null;
		for(User userone :users){
			if(userone.getUserType().equals(User.UserType.ADMIN) || userone.getUserType().equals(User.UserType.CONTROLMANAGER)|| userone.getUserType().equals(User.UserType.EMPLOYEE)){
				user = userone;
			}
		}
		if (user == null) {
			throw new EntityNotFoundException("用户不存在");
		}
		if(user.getUserType() == UserType.USER){
			return null;
		}
		if(user.getRole() != null){
			if(user.getRole().getState() == Role.State.OFF){
				SecurityUtils.getSubject().getSession().setAttribute(Constant.SYS_ERROR_LOGIN_USER_DISABLED, Constant.SYS_ERROR_LOGIN_USER_DISABLED);
				return null;
			}
		}
		if(user.getState()==State.OFF){//禁用
			SecurityUtils.getSubject().getSession().setAttribute(Constant.SYS_ERROR_LOGIN_USER_DISABLED, Constant.SYS_ERROR_LOGIN_USER_DISABLED);
			return null;
		}
		if(user.getState()==State.DEL){//删除
			return null;
		}
		if(user.getRole()!=null){
			if(!user.getRole().getPermissionList().contains("LOGIN_MANAGER_BACK")){
				SecurityUtils.getSubject().getSession().setAttribute(Constant.SYS_ERROR_LOGIN_PERMISSION, Constant.SYS_ERROR_LOGIN_PERMISSION);
				return null;
			}
		}
		byte[] salt = Encodes.decodeHex(user.getSalt());
		// 交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
				new ShiroUser(user), // 用户名
				user.getPassword(), // 密码
				ByteSource.Util.bytes(salt),//
				getName() // realm name
		);
		return authenticationInfo;
	}

	/**
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		ShiroUser shiroUser = GlobalData.getCurrentUser();
		User user = userService.findUserById(shiroUser.getId());
		
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		if(user.getUserType() == UserType.ADMIN){
			authorizationInfo.addRole(UserType.ADMIN.name());
			authorizationInfo.addStringPermissions(Permission.allPermissions);
		}else {
			authorizationInfo.addRole(user.getUserType().name());
			authorizationInfo.addStringPermissions(user.getRole().getPermissionList());
		}
		return authorizationInfo;

	}

	/*
	 * (non-Javadoc) 清理认证缓存
	 * 
	 * @see
	 * org.apache.shiro.realm.AuthorizingRealm#clearCachedAuthorizationInfo(
	 * org.apache.shiro.subject.PrincipalCollection)
	 */
	@Override
	public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
		super.clearCachedAuthorizationInfo(principals);
	}

	/**
	 * 设定Password校验的Hash算法与迭代次数.
	 */
	@PostConstruct
	public void initCredentialsMatcher() {
		SHA1Encrypt encrypt = SHA1Encrypt.getInstance();
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(
				encrypt.getHashAlgorithName());
		matcher.setHashIterations(encrypt.getHashIterations());
		setCredentialsMatcher(matcher);
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

}
