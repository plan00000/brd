package com.zzy.brd.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicUpdate;

import com.zzy.brd.entity.interfaces.IdEntity;

/**
 * @author:xpk
 *    2016年9月22日-下午5:19:08
 **/
@Entity
@Table(name = "loginlog")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate(value = true)
public class Loginlog extends IdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8058469861060473385L;
	
	@ManyToOne(targetEntity=User.class,fetch=FetchType.LAZY)
	@JoinColumn(name="user_id",referencedColumnName="id")
	private User user ;
	
	@Column(name="login_date")
	private Date loginDate;
	
	@Column(name="user_login_ip")
	private String userLoginIp;
	
	@Column(name="login_address")
	private String loginAddress;
	
	@Column(name="login_times")
	private int loginTimes;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	public String getUserLoginIp() {
		return userLoginIp;
	}

	public void setUserLoginIp(String userLoginIp) {
		this.userLoginIp = userLoginIp;
	}

	public String getLoginAddress() {
		return loginAddress;
	}

	public void setLoginAddress(String loginAddress) {
		this.loginAddress = loginAddress;
	}

	public int getLoginTimes() {
		return loginTimes;
	}

	public void setLoginTimes(int loginTimes) {
		this.loginTimes = loginTimes;
	}
	
}
