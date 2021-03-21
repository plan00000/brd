/*******************************************************************************
 * Copyright (c) 2005, 2014 zzy.cn
 *
 * 
 *******************************************************************************/
package com.zzy.brd.dto.req.admin.role;

import com.zzy.brd.entity.Role.State;


/**
 * 角色DTO
 * 
 * @author lzh 2016年9月23日
 */
public class ReqRoleDTO {
	/** 角色名称 */
	private String rolename;
	/** 权限 */
	private String permissions;
	
	public String getRolename() {
		return rolename;
	}
	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
	public String getPermissions() {
		return permissions;
	}
	public void setPermissions(String permissions) {
		this.permissions = permissions;
	}
}
