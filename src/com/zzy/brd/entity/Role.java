package com.zzy.brd.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicUpdate;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.zzy.brd.entity.interfaces.IdEntity;

/***
 * 角色表
 * 
 * @author wwy
 *
 */
@Entity
@Table(name = "role")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate(value = true)
public class Role extends IdEntity {
	/**
	 * 
	 */
	public enum State{
		OFF,ON
	}
	private static final long serialVersionUID = 1820717433366616043L;
	/** 角色名称 */
	private String rolename;
	/** 权限 */
	private String permissions;
	/**状态*/
	private State state;
	
	/**角色人数*/
	private int number;
	
	@Transient
	public List<String> getPermissionList() {
		String[] per = StringUtils.split(this.permissions, ";");
		if (per == null) {
			return ImmutableList.of();
		}
		return ImmutableList.copyOf(per);
	}
	
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

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
}
