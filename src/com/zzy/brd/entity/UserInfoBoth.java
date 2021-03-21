package com.zzy.brd.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicUpdate;

import com.zzy.brd.entity.UserInfoEmployee.AppLoginStatus;
import com.zzy.brd.entity.UserInfoEmployee.AppType;
import com.zzy.brd.entity.interfaces.IdEntity;

/**
 * 用户信息表-用户及业务员共有
 * @author lzh 2016-9-23
 *
 */
@Entity
@Table(name = "user_info_both")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate(value = true)
public class UserInfoBoth extends IdEntity implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6343961425778317819L;
	public enum StarOrderAwardFlag {
		/** 未发放*/
		NO,
		/** 已发放*/
		YES;
	}
	public enum ApprenticeAwardFlag {
		/** 未发放*/
		NO,
		/** 已发放*/
		YES;
	}
	/** 提现密码*/
	public String withdrawPassword = "";
	/** 提现密码的盐*/
	public String withdrawSalt = "";
	/** 活动收徒弟数量*/
	public int acitivitySonSum;
	/** 徒弟数量*/
	public int sonSum;
	/** 徒孙数量*/
	public int grandsonSum;
	/** 徒孙孙数量*/
	public int ggsonsSum;
	
	/** 订单提成佣金）*/
	public BigDecimal orderBrokerage;
	/** 活动奖励佣金）*/
	public BigDecimal activityBrokerage;
	/** 收徒奖励佣金*/
	public BigDecimal apprenticeAwareBrokerage;
	/** 佣金（已提取）*/
	public BigDecimal brokerageHaveWithdraw;
	/** 佣金（可提现）*/
	public BigDecimal brokerageCanWithdraw ;
	/** 佣金（提现中）*/
	public BigDecimal brokerageWithdrawing ;
	/** 订单总数*/
	public int orderSum;
	/** 徒弟订单总数*/
	public int sonsOrderSum;
	/** 徒孙订单总数*/
	public int gsonsOrderSum;
	/** 徒孙孙订单总数*/
	public int ggsonsOrderSum;
	/** 订单成功总数*/
	public int orderSuccessSum;
	/** 徒弟订单总额*/
	public BigDecimal sonsOrderMoney;
	/** 徒孙订单总额*/
	public BigDecimal gsonsOrderMoney;
	/** 徒孙孙订单总额*/
	public BigDecimal ggsonsOrderMoney;
	/** 我的订单总额*/
	public BigDecimal orderMoney;	
	/** 当前登录设备的类型 */
	private AppType loginDeviceApptype;
	/** 当前登录设备的推送码 */
	private String loginDeviceSeq;
	/** 登录状态 */
	private AppLoginStatus appLoginStatus;
	
	/** 星级订单奖励发放标志*/
	public StarOrderAwardFlag starOrderAwardFlag;
	/** 收徒奖励发放标志, 0-未发放，1-已发放*/
	public ApprenticeAwardFlag apprenticeAwardFlag;
	
	/** 师公*/
	@ManyToOne(targetEntity = User.class,fetch=FetchType.LAZY)
	@JoinColumn(name = "grand_parentid",referencedColumnName = "id")
	private User grandParent;
	
	/** 师傅*/
	@ManyToOne(targetEntity = User.class,fetch=FetchType.LAZY)
	@JoinColumn(name = "parentid",referencedColumnName = "id")
	private User parent;
	
	/** 商家*/
	@ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "sellerid",referencedColumnName = "id")
	private User seller;
	
	/** 业务员*/
	@ManyToOne(targetEntity = User.class , fetch = FetchType.LAZY)
	@JoinColumn(name = "salesmanid", referencedColumnName="id")
	private User salesman;
	
	/** 原业务员id */
	@ManyToOne(targetEntity = User.class , fetch = FetchType.LAZY)
	@JoinColumn(name = "past_salesmanid", referencedColumnName="id")
	private User pastSalesman;
	
	/**二维码*/
	@Column(name="qr_code")
	private String qrCode;
	
	/**推荐码*/
	@Column(name="recommend_code")
	private String recommendCode;
	
	/**身份证*/
	@Column(name="expands")
	private String expands;
	
	@PrePersist
	public void prepareForInsert() {
		//Date date = new Date();
		this.brokerageHaveWithdraw = BigDecimal.ZERO;
	//	this.activityBrokerage = BigDecimal.ZERO;
		this.orderBrokerage = BigDecimal.ZERO;
	//	this.brokerageCanWithdraw = BigDecimal.ZERO;
		this.brokerageWithdrawing = BigDecimal.ZERO;
		this.appLoginStatus = AppLoginStatus.NOT_LOGIN;
		this.sonsOrderMoney = BigDecimal.ZERO;
		this.orderMoney = BigDecimal.ZERO;
		this.gsonsOrderMoney = BigDecimal.ZERO;
		this.ggsonsOrderMoney = BigDecimal.ZERO;
		this.apprenticeAwardFlag =ApprenticeAwardFlag.NO;
		this.starOrderAwardFlag = StarOrderAwardFlag.NO;
		this.loginDeviceApptype = AppType.ANDROID;
	}
	public String getWithdrawPassword() {
		return withdrawPassword;
	}
	public void setWithdrawPassword(String withdrawPassword) {
		this.withdrawPassword = withdrawPassword;
	}
	public String getWithdrawSalt() {
		return withdrawSalt;
	}
	public void setWithdrawSalt(String withdrawSalt) {
		this.withdrawSalt = withdrawSalt;
	}
	public int getSonSum() {
		return sonSum;
	}
	public void setSonSum(int sonSum) {
		this.sonSum = sonSum;
	}
	public int getGrandsonSum() {
		return grandsonSum;
	}
	public void setGrandsonSum(int grandsonSum) {
		this.grandsonSum = grandsonSum;
	}
	public int getGgsonsSum() {
		return ggsonsSum;
	}
	public void setGgsonsSum(int ggsonsSum) {
		this.ggsonsSum = ggsonsSum;
	}
	public BigDecimal getBrokerageHaveWithdraw() {
		return brokerageHaveWithdraw;
	}
	public void setBrokerageHaveWithdraw(BigDecimal brokerageHaveWithdraw) {
		this.brokerageHaveWithdraw = brokerageHaveWithdraw;
	}
	public BigDecimal getBrokerageCanWithdraw() {
		return brokerageCanWithdraw;
	}
	public void setBrokerageCanWithdraw(BigDecimal brokerageCanWithdraw) {
		this.brokerageCanWithdraw = brokerageCanWithdraw;
	}
	public BigDecimal getBrokerageWithdrawing() {
		return brokerageWithdrawing;
	}
	public void setBrokerageWithdrawing(BigDecimal brokerageWithdrawing) {
		this.brokerageWithdrawing = brokerageWithdrawing;
	}
	public int getOrderSum() {
		return orderSum;
	}
	public void setOrderSum(int orderSum) {
		this.orderSum = orderSum;
	}
	public int getSonsOrderSum() {
		return sonsOrderSum;
	}
	public void setSonsOrderSum(int sonsOrderSum) {
		this.sonsOrderSum = sonsOrderSum;
	}
	public int getGsonsOrderSum() {
		return gsonsOrderSum;
	}
	public void setGsonsOrderSum(int gsonsOrderSum) {
		this.gsonsOrderSum = gsonsOrderSum;
	}
	public int getGgsonsOrderSum() {
		return ggsonsOrderSum;
	}
	public void setGgsonsOrderSum(int ggsonsOrderSum) {
		this.ggsonsOrderSum = ggsonsOrderSum;
	}
	public int getOrderSuccessSum() {
		return orderSuccessSum;
	}
	public void setOrderSuccessSum(int orderSuccessSum) {
		this.orderSuccessSum = orderSuccessSum;
	}
	public BigDecimal getSonsOrderMoney() {
		return sonsOrderMoney;
	}
	public void setSonsOrderMoney(BigDecimal sonsOrderMoney) {
		this.sonsOrderMoney = sonsOrderMoney;
	}
	public BigDecimal getGsonsOrderMoney() {
		return gsonsOrderMoney;
	}
	public void setGsonsOrderMoney(BigDecimal gsonsOrderMoney) {
		this.gsonsOrderMoney = gsonsOrderMoney;
	}
	public BigDecimal getGgsonsOrderMoney() {
		return ggsonsOrderMoney;
	}
	public void setGgsonsOrderMoney(BigDecimal ggsonsOrderMoney) {
		this.ggsonsOrderMoney = ggsonsOrderMoney;
	}
	public AppType getLoginDeviceApptype() {
		return loginDeviceApptype;
	}
	public void setLoginDeviceApptype(AppType loginDeviceApptype) {
		this.loginDeviceApptype = loginDeviceApptype;
	}
	public String getLoginDeviceSeq() {
		return loginDeviceSeq;
	}
	public void setLoginDeviceSeq(String loginDeviceSeq) {
		this.loginDeviceSeq = loginDeviceSeq;
	}
	public AppLoginStatus getAppLoginStatus() {
		return appLoginStatus;
	}
	public void setAppLoginStatus(AppLoginStatus appLoginStatus) {
		this.appLoginStatus = appLoginStatus;
	}
	public StarOrderAwardFlag getStarOrderAwardFlag() {
		return starOrderAwardFlag;
	}
	public void setStarOrderAwardFlag(StarOrderAwardFlag starOrderAwardFlag) {
		this.starOrderAwardFlag = starOrderAwardFlag;
	}
	public ApprenticeAwardFlag getApprenticeAwardFlag() {
		return apprenticeAwardFlag;
	}
	public void setApprenticeAwardFlag(ApprenticeAwardFlag apprenticeAwardFlag) {
		this.apprenticeAwardFlag = apprenticeAwardFlag;
	}
	public User getGrandParent() {
		return grandParent;
	}
	public void setGrandParent(User grandParent) {
		this.grandParent = grandParent;
	}
	public User getParent() {
		return parent;
	}
	public void setParent(User parent) {
		this.parent = parent;
	}
	public User getSeller() {
		return seller;
	}
	public void setSeller(User seller) {
		this.seller = seller;
	}
	public User getSalesman() {
		return salesman;
	}
	public void setSalesman(User salesman) {
		this.salesman = salesman;
	}
	public String getQrCode() {
		return qrCode;
	}
	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}
	public String getRecommendCode() {
		return recommendCode;
	}
	public void setRecommendCode(String recommendCode) {
		this.recommendCode = recommendCode;
	}
	
	public String getExpands() {
		return expands;
	}
	public void setExpands(String expands) {
		this.expands = expands;
	}
	public User getPastSalesman() {
		return pastSalesman;
	}
	public void setPastSalesman(User pastSalesman) {
		this.pastSalesman = pastSalesman;
	}
	public BigDecimal getOrderBrokerage() {
		return orderBrokerage;
	}
	public void setOrderBrokerage(BigDecimal orderBrokerage) {
		this.orderBrokerage = orderBrokerage;
	}
	public BigDecimal getActivityBrokerage() {
		return activityBrokerage;
	}
	public void setActivityBrokerage(BigDecimal activityBrokerage) {
		this.activityBrokerage = activityBrokerage;
	}
	public int getAcitivitySonSum() {
		return acitivitySonSum;
	}
	public void setAcitivitySonSum(int acitivitySonSum) {
		this.acitivitySonSum = acitivitySonSum;
	}
	public BigDecimal getOrderMoney() {
		return orderMoney;
	}
	public void setOrderMoney(BigDecimal orderMoney) {
		this.orderMoney = orderMoney;
	}
	public BigDecimal getApprenticeAwareBrokerage() {
		return apprenticeAwareBrokerage;
	}
	public void setApprenticeAwareBrokerage(BigDecimal apprenticeAwareBrokerage) {
		this.apprenticeAwareBrokerage = apprenticeAwareBrokerage;
	}
	
}
