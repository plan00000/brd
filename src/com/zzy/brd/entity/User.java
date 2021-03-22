package com.zzy.brd.entity;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Formula;

import com.zzy.brd.constant.ConfigSetting;
import com.zzy.brd.entity.interfaces.IUser;
import com.zzy.brd.entity.interfaces.IdEntity;

/***
 * 用户表
 * 
 * @author lzh
 *
 */
@Entity
@Table(name = "user")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate(value = true)
public class User extends IdEntity implements java.io.Serializable, IUser {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5344363857919063737L;

	/** 状态 */
	public enum State {
		OFF, ON, DEL, ;
		public String getStr() {
			if (this == State.ON) {
				return "启用";
			}
			if (this == State.OFF) {
				return "禁用";
			}
			return "";
		}
	};

	/** 用户类型 */
	public enum UserType {
		/** 用户 */
		USER,
		/** 融资经理*/
		MANAGER,
		/** 商家*/
		SELLER,
		/** 风控经理*/
		CONTROLMANAGER,
		/** 业务员*/
		SALESMAN,
		/** CEO*/
		ADMIN,
		/***员工*/
		EMPLOYEE;
		public String getStr() {
			if (this == USER) {
				return "普通用户";
			}
			if (this == MANAGER) {
				return "融资经理";
			}
			if (this == SELLER) {
				return "商家";
			}
			if (this == CONTROLMANAGER) {
				return "风控经理";
			}
			if (this == SALESMAN) {
				return "业务员";
			}
			if (this == ADMIN) {
				return "管理员";
			}
			if (this == EMPLOYEE) {
				return "员工";
			}
			return "";
		}
	}

	/** 用户名 */
	private String username = "";

	/** 姓名 */
	private String realname = "";

	/** 头像图片地址 */
	private String headimgurl = "";

	/** 密码 */
	private String password;

	/** 加密字段 */
	private String salt;

	/** 手机号 */
	private String mobileno;

	/** 状态 */
	private State state;

	/** 用户类型 */
	private UserType userType;
	
	/** 创建时间*/
	private Date createdate;
	/** 登录次数*/
	private int loginTimes  = 0;


	/** 本次登录时间 */
	private Date logindate;
	/** 最后一次登录时间 */
	private Date lastlogindate;
	
	/** 角色 */
	@ManyToOne(targetEntity = Role.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "role_id", referencedColumnName = "id")
	private Role role;
	
	/**
	 * @param id
	 */
	public User(Long id) {
		this.id = id;
	}
	
	public User() {
	}

	/**
	 * 返回相关状态
	 * 
	 * @param @return
	 * @return String
	 */
	public String getStateStr() {
		if (state == State.ON) {
			return "启用";
		}
		if (state == State.OFF) {
			return "禁用";
		}
		return "";
	}

	/**
	 * app获取图片显示链接
	 * 
	 * @return
	 */
	public String getAppHeadimgurl() {
		if (headimgurl == "") {
			return "";
		}
		return ConfigSetting.appfileProUrlByFilePath(headimgurl);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	/**
	 * @return the headimgurl
	 */
	public String getHeadimgurl() {
		return headimgurl;
	}

	/**
	 * @param headimgurl
	 *            the headimgurl to set
	 */
	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public String getMobileno() {
		return mobileno;
	}

	public void setMobileno(String mobileno) {
		this.mobileno = mobileno;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public int getLoginTimes() {
		return loginTimes;
	}

	public void setLoginTimes(int loginTimes) {
		this.loginTimes = loginTimes;
	}
	
	public Date getLogindate() {
		return logindate;
	}
	public void setLogindate(Date logindate) {
		this.logindate = logindate;
	}
	public Date getLastlogindate() {
		return lastlogindate;
	}
	public void setLastlogindate(Date lastlogindate) {
		this.lastlogindate = lastlogindate;
	}

	
}
