package com.zzy.brd.dto.rep.admin.reportStatistics;

/**
 * 订单统计数
 * 
 * @author csy 20161012
 */
public class RepOrderStatisticsConutDTO {

	/** 全部贷款数 */
	private int totalLoanCount;
	/** 全部放款数 */
	private int totalCreditCount;
	
	/** 昨天贷款数 */
	private int yesLoanCount;
	/** 昨天放款数 */
	private int yesCreditCount;
	public int getTotalLoanCount() {
		return totalLoanCount;
	}
	public void setTotalLoanCount(int totalLoanCount) {
		this.totalLoanCount = totalLoanCount;
	}
	public int getTotalCreditCount() {
		return totalCreditCount;
	}
	public void setTotalCreditCount(int totalCreditCount) {
		this.totalCreditCount = totalCreditCount;
	}
	public int getYesLoanCount() {
		return yesLoanCount;
	}
	public void setYesLoanCount(int yesLoanCount) {
		this.yesLoanCount = yesLoanCount;
	}
	public int getYesCreditCount() {
		return yesCreditCount;
	}
	public void setYesCreditCount(int yesCreditCount) {
		this.yesCreditCount = yesCreditCount;
	}

	
}
