package com.zzy.brd.dto.rep.admin.reportStatistics;

import java.math.BigDecimal;

import com.zzy.brd.entity.Product;
import com.zzy.brd.entity.ProductType;
import com.zzy.brd.entity.User;

/**
 * 产品统计
 * 
 * @author csy 20161018
 */
public class RepProductStatistics {

	/**提单类型 */
	private String billType;
	/**产品数量 */
	private long productCount;
	
	/**所占比例 */
	private String productPercent;
	/**被申请次数 */
	private long applyTimes;
	
	/**已放款订单数 */
	private long loanCount;
	/**放款金额 */
	private BigDecimal loanMoney = BigDecimal.ZERO;;
	
	public RepProductStatistics(long applyTimes ,long productCount,ProductType.BillType billType) {
		this.billType = billType.getDes();
		this.productCount = productCount;
		this.applyTimes = applyTimes;
	}
	public RepProductStatistics(long loanCount ,BigDecimal loanMoney,ProductType.BillType billType) {
		this.billType = billType.getDes();
		this.loanCount = loanCount;
		this.loanMoney = loanMoney;
	}
	public RepProductStatistics() {

	}
	public String getBillType() {
		return billType;
	}
	public void setBillType(String billType) {
		this.billType = billType;
	}
	public long getProductCount() {
		return productCount;
	}
	public void setProductCount(long productCount) {
		this.productCount = productCount;
	}
	public String getProductPercent() {
		return productPercent;
	}
	public void setProductPercent(String productPercent) {
		this.productPercent = productPercent;
	}
	public long getApplyTimes() {
		return applyTimes;
	}
	public void setApplyTimes(long applyTimes) {
		this.applyTimes = applyTimes;
	}
	public long getLoanCount() {
		return loanCount;
	}
	public void setLoanCount(long loanCount) {
		this.loanCount = loanCount;
	}
	public BigDecimal getLoanMoney() {
		return loanMoney;
	}
	public void setLoanMoney(BigDecimal loanMoney) {
		this.loanMoney = loanMoney;
	}
	
}
