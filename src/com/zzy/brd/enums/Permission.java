/*******************************************************************************
 * Copyright (c) 2005, 2014 zzy.cn
 *
 * 
 *******************************************************************************/
package com.zzy.brd.enums;

import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Predicate;
import com.google.common.collect.Sets;

/**
 * 权限
 * @author lzh 2016年9月20日
 */
public enum Permission {
	
	/**
	 * 登录权限
	 */
	/**　前台登录*/
	LOGIN_MANAGER_FRONT,
	/** 后台登录*/
	LOGIN_MANAGER_BACK,
	
	/**
	 * 会员管理
	 */
	/**会员*/
	USER_MANAGER,
	/**升级会员身份**/
	USER_UPGRADE,
	
	/**
	 * 企业管理
	 */
	/**部门管理*/
	ORGANIZATION_MANAGER_DEPART,
	/**员工管理*/
	ORGANIZATION_MANAGER_USER,
	/**角色管理*/
	ORGANIZATION_MANAGER_ROLE,
	
	/**
	 * 订单管理
	 */
	/** 贷款订单*/
	ORDER_MANAGER,
	/** 官网订单*/
	ORDER_MANAGER_PC,
	/** 佣金订单*/
	ORDER_MANAGER_BROKERAGE,
	/** 提现订单*/
	ORDER_MANAGER_WITHDRAW,
	
	/**
	 * 产品管理
	 */
	/*PRODUCT_MANAGER,*/
	/**产品管理*/
	PRODUCT_MANAGER,
	/**产品类别*/
	PRODUCT_MANAGER_TYPE,
	
	/***
	 * 活动管理
	 */
	/** 星级订单*/
	ACTIVITY_MANAGER_STAR,
	/** 收徒奖励*/
	ACTIVITY_MANAGER_APPRENTICE,
	/** 推荐注册*/
	ACTIVITY_MANAGER_REGISTER,
	/** 二维码活动*/
	ACTIVITY_MANAGER_QRCODE,
	
	/** 
	 * 文章管理
	 * */
	/** 活动咨询*/
	TEXT_MANAGER_ACTIVITY,
	/** 帮助中心*/
	TEXT_MANAGER_HELP,
	/** 收徒指南*/
	TEXT_MANAGER_APPRENTICE,
	/** 关于我们*/
	TEXT_MANAGER_ABOUTUS,
	/** 精彩资讯 */
	TEXT_MANGER_PC_ACTIVITY,
	
	/**
	 * 广告管理
	 */
	/** pc官网*/
	AD_MANAGER_PC,
	/** 微信站*/
	AD_MANAGER_WEIXIN,
	
	/**
	 * 平台设置
	 */
	/** 基础设置*/
	SET_MANAGER_BASE,
	/** 消息模块*/
	SET_MANAGER_INFO,
	/** 服务协议*/
	SET_MANAGER_AGGREMENT,
	/** 微信推送*/
	SET_WECHAT_NOTIFY,
	/** 订单通知*/
	SET_ORDERFORM_NOTIFY,
	/**SEO设置*/
	SET_SEO_SETTING,
	/**友情链接*/
	SET_FRIENDSHIPLINK,
	/** 
	 * 报表统计
	 */
	/** 会员统计*/
	STATISTICS_MANAGER_USER,
	/**会员报表 */
	STATISTICS_REPORT_USER,
	/** 订单统计*/
	STATISTICS_MANAGER_ORDER,
	/** 产品统计*/
	STATISTICS_MANAGER_PRODUCT,
	/** 部门统计*/
	STATISTICS_MANAGER_DEPART,
	/** 佣金统计*/
	STATISTICS_MANAGER_BROKERAGE,
	/** 商家统计*/
	STATISTICS_SELLER,
	/**
	 * 系统日志
	 */
	SYS_LOG,
	/**
	 * 导出
	 */
	USER_EXPORT,
	
	;
	
	
	
	public static Set<String> allPermissions = Sets.newHashSet();
	public static String allPermissionStr = "";
	static {
		for (Permission permission : Permission.values()) {
			allPermissions.add(permission.name());
		}
		allPermissionStr = StringUtils.join(allPermissions, ";");
	}
	
	/**
	 * 判断客服是否可以拥有该权限
	 * @return boolean
	 */
	//权限集
	static Set<Permission> availableForCustomerSet = Sets.
		newHashSet();
	public boolean isCanContainForCustomer() {
		if (availableForCustomerSet.contains(this)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 转换enum
	 * @param permissions
	 * @return
	 */
	public static Set<Permission> permissions(String[] permissions) {
		Set<Permission> set = Sets.newHashSet();
		for (String permission : permissions) {
			Permission permissionT = Permission.valueOf(permission);
			if (permissionT != null) {
				set.add(permissionT);
			}
		}
		return set;
	}
	
	/**
	 * 解析权限集字符串
	 * @param permissionStr
	 * @return
	 */
	public static Set<Permission> permissions(String permissionStr) {
		Set<Permission> set = Sets.newHashSet();
		for (String permission : StringUtils.split(permissionStr,";")) {
			Permission per = value(permission);
			if (per != null) {
				set.add(per);
			}
		}
		return set;
	}
	
	/**
	 * 获取权限集字符串
	 * @param permissions
	 * @return
	 */
	public static String permissionsStr(Set<Permission> permissions) {
		if (permissions == null || permissions.size() == 0) {
			return "";
		}
		return StringUtils.join(permissions, ";");
	}
	
	/**
	 * 过滤权限集
	 * @param permissions
	 * @return
	 */
	public static String permissionsStr(String permissions) {
		return permissionsStr(permissions(permissions));
	}
	
	/**
	 * 客服 过滤非法权限集
	 * @param permissions
	 * @return
	 */
	public static String permissionsStrForCustomer(String permissions) {
		//过滤非法权限
		Set<Permission> set = Sets.filter(permissions(permissions), new Predicate<Permission>() {
			@Override
			public boolean apply(Permission permission) {
				return permission.isCanContainForCustomer();
			}
		});
		return permissionsStr(set);
	}
	
	public static Permission value(String value) {
		if (allPermissions.contains(value)) {
			return Permission.valueOf(value);
		}
		return null;
	}
	
}
