package com.zzy.brd.mobile.web.dto.rep.apprentice;

import java.math.BigDecimal;
import java.util.Date;

import com.zzy.brd.entity.User;

/**
 * 我的徒弟列表
 * @author lzh 2016/11/21
 *
 */
public class RepMyApprenticeDTO {
	/** 昵称*/
	private String userName;
	/** 用户类型*/
	private int userType;
	/** 手机号*/
	private String mobileno;
	/** 注册时间*/
	private Date createdate;
	/** 收徒人数*/
	private int sonSum;
	/** 赚佣多少*/
	private BigDecimal brokerage;
	
	public RepMyApprenticeDTO(User user,int brokerage){
		this.userName = user.getUsername();
		this.userType = user.getUserType().ordinal();
		this.mobileno = user.getMobileno();
		this.createdate = user.getCreatedate();
		this.sonSum = user.getUserInfoBoth().getSonSum();
		//this.brokerage = brokerage;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getUserType() {
		return userType;
	}
	public void setUserType(int userType) {
		this.userType = userType;
	}
	public String getMobileno() {
		return mobileno;
	}
	public void setMobileno(String mobileno) {
		this.mobileno = mobileno;
	}
	public Date getCreatedate() {
		return createdate;
	}
	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}
	public int getSonSum() {
		return sonSum;
	}
	public void setSonSum(int sonSum) {
		this.sonSum = sonSum;
	}
	public BigDecimal getBrokerage() {
		return brokerage;
	}
	public void setBrokerage(BigDecimal brokerage) {
		this.brokerage = brokerage;
	}
	
}
