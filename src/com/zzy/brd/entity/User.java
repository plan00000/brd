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
	
	/** 用户信息 -员工的*/
	@OneToOne(targetEntity = UserInfoEmployee.class,cascade=CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name = "user_info_employee_id",referencedColumnName = "id",unique = true)
	private UserInfoEmployee userInfoEmployee;
	
	/** 用户信息-会员、商家、融资经理、业务员共有*/
	/*@OneToOne(targetEntity = UserInfoBoth.class,cascade=CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name = "user_info_both_id", referencedColumnName = "id",unique = true)
	private UserInfoBoth userInfoBoth;*/
	
	/**用户信息-商家信息*/
	/*@OneToOne(targetEntity = UserInfoSeller.class,cascade=CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name="user_info_seller_id",referencedColumnName="id",unique=true)
	private UserInfoSeller userInfoSeller;*/
	
	/** 微信用户信息表*/
	/*@OneToOne(targetEntity = WeixinUser.class, fetch = FetchType.LAZY,cascade= CascadeType.ALL)
	@JoinColumn(name = "weixin_user_id", referencedColumnName = "id",unique = true)
	private WeixinUser weixinUser;*/
	
	/** 角色 */
	@ManyToOne(targetEntity = Role.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "role_id", referencedColumnName = "id")
	private Role role;
	
	/**备注*/
	/*@OneToMany(targetEntity=UserRemark.class,mappedBy="user",fetch=FetchType.LAZY)
	private List<UserRemark> remarks = new ArrayList<>();*/
	
	/** 徒弟赚佣*/

	/*@Formula(value = "(select ifnull(sum(r.have_brokerage),0) from record_brokerage r left join user u "
			+ "on r.gain_user_id = u.id left join user_info_both uib on r.gain_infoboth_id = uib.id where r.relate =1 and u.id = id and uib.parentid = r.user_id group by r.gain_user_id)")
	@Basic(fetch = FetchType.LAZY)
	private BigDecimal brokerage;*/
	
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

	public UserInfoEmployee getUserInfoEmployee() {
		return userInfoEmployee;
	}

	public void setUserInfoEmployee(UserInfoEmployee userInfoEmployee) {
		this.userInfoEmployee = userInfoEmployee;
	}

	/*public UserInfoBoth getUserInfoBoth() {
		return userInfoBoth;
	}

	public void setUserInfoBoth(UserInfoBoth userInfoBoth) {
		this.userInfoBoth = userInfoBoth;
	}*/

	/*public WeixinUser getWeixinUser() {
		return weixinUser;
	}

	public void setWeixinUser(WeixinUser weixinUser) {
		this.weixinUser = weixinUser;
	}*/

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

	/*public List<UserRemark> getRemarks() {
		return remarks;
	}

	public void setRemarks(List<UserRemark> remarks) {
		this.remarks = remarks;
	}*/

	/*public BigDecimal getBrokerage() {
		return brokerage;
	}

	public void setBrokerage(BigDecimal brokerage) {
		this.brokerage = brokerage;
	}*/

	/*public UserInfoSeller getUserInfoSeller() {
		return userInfoSeller;
	}

	public void setUserInfoSeller(UserInfoSeller userInfoSeller) {
		this.userInfoSeller = userInfoSeller;
	}*/
	
}
