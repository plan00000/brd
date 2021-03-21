package com.zzy.brd.dto.rep.admin.reportStatistics;

/**
 * 会员统计数
 * 
 * @author csy 20161012
 */
public class RepUserStatisticsConutDTO {

	/** 全部普通会员数 */
	private int totalUserCount;
	/** 全部融资经理数 */
	private int totalManagerCount;
	/** 全部商家数 */
	private int totalSellerCount;
	/** 昨天普通会员数 */
	private int yesUserCount;
	/** 昨天融资经理数 */
	private int yesManagerCount;
	/** 昨天商家数 */
	private int yesSellerCount;
	public int getTotalUserCount() {
		return totalUserCount;
	}
	public void setTotalUserCount(int totalUserCount) {
		this.totalUserCount = totalUserCount;
	}
	public int getTotalManagerCount() {
		return totalManagerCount;
	}
	public void setTotalManagerCount(int totalManagerCount) {
		this.totalManagerCount = totalManagerCount;
	}
	public int getTotalSellerCount() {
		return totalSellerCount;
	}
	public void setTotalSellerCount(int totalSellerCount) {
		this.totalSellerCount = totalSellerCount;
	}
	public int getYesUserCount() {
		return yesUserCount;
	}
	public void setYesUserCount(int yesUserCount) {
		this.yesUserCount = yesUserCount;
	}
	public int getYesManagerCount() {
		return yesManagerCount;
	}
	public void setYesManagerCount(int yesManagerCount) {
		this.yesManagerCount = yesManagerCount;
	}
	public int getYesSellerCount() {
		return yesSellerCount;
	}
	public void setYesSellerCount(int yesSellerCount) {
		this.yesSellerCount = yesSellerCount;
	}

}
