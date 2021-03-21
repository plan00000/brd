package com.zzy.brd.dto.rep.admin.reportStatistics;

import java.math.BigDecimal;

import com.zzy.brd.entity.Product;
import com.zzy.brd.entity.ProductInfo;
import com.zzy.brd.entity.ProductType;
import com.zzy.brd.entity.Product.InterestType;

/**
 * 产品统计
 * 
 * @author csy 20161018
 */
public class RepProductStatisticsList {

	/**产品Id */
	private long id;
	/**产品名称 */
	private String paoductName;
	/**提单类型 */
	private String billType;
	/**产品分类*/
	private String typeName;
	/**贷款最小金额*/
	private BigDecimal loanMinAmount = BigDecimal.ZERO;
	/**贷款最大金额*/
	private BigDecimal loanMaxAmount = BigDecimal.ZERO;
	/**贷款最小期限*/
	private int loanMinTime;
	/**贷款最大期限*/
	private int loanMaxTime;
	/**贷款利率*/
	private String loanRate;
	/**被申请次数 */
	private long applyTimes;
	/**放款金额 */
	private BigDecimal loanMoney = BigDecimal.ZERO;
	/**利息类型 */
	private int interestType;
	
	public RepProductStatisticsList(
			Product product,ProductType productType,ProductInfo productInfo,BigDecimal loanMoney) {
		this.id = product.getId();
		this.paoductName = productInfo.getProductName();
		this.billType = productType.getBillType().getDes();
		this.typeName = productType.getProductName();
		this.loanMinAmount =productInfo.getLoanMinAmount();
		this.loanMaxAmount = productInfo.getLoanMaxAmount();
		this.loanMinTime =productInfo.getLoanMinTime();
		this.loanMaxTime = productInfo.getLoanMaxTime();
		this.loanRate = productInfo.getLoanRate() +"";
		this.applyTimes = product.getApplyTimes();
		InterestType interestType1 = product.getInterestType();
		if(interestType1==InterestType.INTERESTMODELDAY){
			this.interestType=1;
		}else{
			this.interestType=2;
		}
		if(loanMoney==null){
			loanMoney =  BigDecimal.ZERO;
		}
		this.loanMoney = loanMoney;
	}

	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPaoductName() {
		return paoductName;
	}


	public void setPaoductName(String paoductName) {
		this.paoductName = paoductName;
	}


	public String getBillType() {
		return billType;
	}


	public void setBillType(String billType) {
		this.billType = billType;
	}


	public String getTypeName() {
		return typeName;
	}


	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}


	public BigDecimal getLoanMinAmount() {
		return loanMinAmount;
	}


	public void setLoanMinAmount(BigDecimal loanMinAmount) {
		this.loanMinAmount = loanMinAmount;
	}


	public BigDecimal getLoanMaxAmount() {
		return loanMaxAmount;
	}


	public void setLoanMaxAmount(BigDecimal loanMaxAmount) {
		this.loanMaxAmount = loanMaxAmount;
	}


	public int getLoanMinTime() {
		return loanMinTime;
	}


	public void setLoanMinTime(int loanMinTime) {
		this.loanMinTime = loanMinTime;
	}


	public int getLoanMaxTime() {
		return loanMaxTime;
	}


	public void setLoanMaxTime(int loanMaxTime) {
		this.loanMaxTime = loanMaxTime;
	}


	public String getLoanRate() {
		return loanRate;
	}


	public void setLoanRate(String loanRate) {
		this.loanRate = loanRate;
	}


	public long getApplyTimes() {
		return applyTimes;
	}


	public void setApplyTimes(long applyTimes) {
		this.applyTimes = applyTimes;
	}


	public BigDecimal getLoanMoney() {
		return loanMoney;
	}


	public void setLoanMoney(BigDecimal loanMoney) {
		this.loanMoney = loanMoney;
	}



	public int getInterestType() {
		return interestType;
	}



	public void setInterestType(int interestType) {
		this.interestType = interestType;
	}

	
}
