package com.zzy.brd.dto.rep.admin.reportStatistics;

import com.zzy.brd.entity.User;

/**
 * @author:xpk
 *    2016年10月21日-下午4:14:46
 **/
public class RepUserReportStatisticDTO {	
	private User user;
	
	private User parent;
	
	private User seller;
	
	private User salesman;
	
	private long sonsSum;
	
	private long grandSonsSum;
	
	private long ggrandSonsSum;
	
	private long orderSum;
	
	private long orderSuccessSum;	
	
	private long loginTimes;
	
	public RepUserReportStatisticDTO(User user,long sonSum,long grandSonSum,long ggrandSonSum,long orderSum,long orderSuccessSum,long loginTimes) {
		this.user = user;	
		this.parent = user.getUserInfoBoth().getParent();
		this.seller = user.getUserInfoBoth().getSeller();
		this.salesman = user.getUserInfoBoth().getSalesman();
		this.sonsSum = sonSum;
		this.grandSonsSum  =grandSonSum;
		this.ggrandSonsSum = ggrandSonSum;
		this.orderSum = orderSum;
		this.orderSuccessSum = orderSuccessSum;
		this.loginTimes = loginTimes;
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


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public long getSonsSum() {
		return sonsSum;
	}


	public void setSonsSum(long sonsSum) {
		this.sonsSum = sonsSum;
	}


	public long getGrandSonsSum() {
		return grandSonsSum;
	}


	public void setGrandSonsSum(long grandSonsSum) {
		this.grandSonsSum = grandSonsSum;
	}


	public long getGgrandSonsSum() {
		return ggrandSonsSum;
	}


	public void setGgrandSonsSum(long ggrandSonsSum) {
		this.ggrandSonsSum = ggrandSonsSum;
	}


	public long getOrderSum() {
		return orderSum;
	}


	public void setOrderSum(long orderSum) {
		this.orderSum = orderSum;
	}


	public long getOrderSuccessSum() {
		return orderSuccessSum;
	}


	public void setOrderSuccessSum(long orderSuccessSum) {
		this.orderSuccessSum = orderSuccessSum;
	}


	public long getLoginTimes() {
		return loginTimes;
	}


	public void setLoginTimes(long loginTimes) {
		this.loginTimes = loginTimes;
	}

}
