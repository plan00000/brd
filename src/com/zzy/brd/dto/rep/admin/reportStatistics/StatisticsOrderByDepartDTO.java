package com.zzy.brd.dto.rep.admin.reportStatistics;

import java.math.BigDecimal;

/**
 * @author csy 2016年10月19日
 */
public class StatisticsOrderByDepartDTO {
	private long totalTime;//部门的总订单数
	private long yestodayTime;//部门的昨日订单数
	private BigDecimal totalMoney = BigDecimal.ZERO;//部门的订单总额
	private BigDecimal yestodayMoney = BigDecimal.ZERO;//部门的昨日总额
	private long managerCount;//融资经理数目
	private long sellerCount;//商家数目

	public long getManagerCount() {
		return managerCount;
	}

	public void setManagerCount(long managerCount) {
		this.managerCount = managerCount;
	}

	public long getSellerCount() {
		return sellerCount;
	}

	public void setSellerCount(long sellerCount) {
		this.sellerCount = sellerCount;
	}

	/**
	 * @return the totalTime
	 */
	public long getTotalTime() {
		return totalTime;
	}

	/**
	 * @param totalTime
	 *            the totalTime to set
	 */
	public void setTotalTime(long totalTime) {
		this.totalTime = totalTime;
	}



	/**
	 * @return the totalMoney
	 */
	public BigDecimal getTotalMoney() {
		return totalMoney;
	}

	/**
	 * @param totalMoney
	 *            the totalMoney to set
	 */
	public void setTotalMoney(BigDecimal totalMoney) {
		this.totalMoney = totalMoney;
	}

	public long getYestodayTime() {
		return yestodayTime;
	}

	public void setYestodayTime(long yestodayTime) {
		this.yestodayTime = yestodayTime;
	}

	public BigDecimal getYestodayMoney() {
		return yestodayMoney;
	}

	public void setYestodayMoney(BigDecimal yestodayMoney) {
		this.yestodayMoney = yestodayMoney;
	}

}
