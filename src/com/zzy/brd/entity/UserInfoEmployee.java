package com.zzy.brd.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicUpdate;

import com.zzy.brd.entity.interfaces.IdEntity;

/**
 * 用户信息表-后台用户
 * @author lzh 2016-9-23
 *
 */
@Entity
@Table(name = "user_info_employee")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate(value = true)
public class UserInfoEmployee extends IdEntity implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7916030969257864695L;
	
	public enum AppType {
		ANDROID, IOS;
	}
	public enum AppLoginStatus{
		/** 注销、未登录状态 */
		NOT_LOGIN,
		/** 登录状态 */
		IS_LOGIN;
	}
	/** 用户编号 */
	private String userno = "";

	/** 登录ip */
	private String loginIp = "";
	/** 本次登录地点*/
	private String loginCity = "";
	/** 上一次登录ip */
	private String lastloginIp = "";
	/** 上一次登录IP所在地区 */
	private String lastloginCity = "";
	
	@OneToOne(targetEntity=User.class,mappedBy="userInfoEmployee")
	private User user;
	
	/** 所属部门 */
	@ManyToOne(targetEntity = Department.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "depart_id", referencedColumnName = "id")
	private Department department;
	
	public String getUserno() {
		return userno;
	}
	public void setUserno(String userno) {
		this.userno = userno;
	}

	public String getLoginIp() {
		return loginIp;
	}
	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}
	public String getLoginCity() {
		return loginCity;
	}
	public void setLoginCity(String loginCity) {
		this.loginCity = loginCity;
	}
	public String getLastloginIp() {
		return lastloginIp;
	}
	public void setLastloginIp(String lastloginIp) {
		this.lastloginIp = lastloginIp;
	}
	public String getLastloginCity() {
		return lastloginCity;
	}
	public void setLastloginCity(String lastloginCity) {
		this.lastloginCity = lastloginCity;
	}
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}
