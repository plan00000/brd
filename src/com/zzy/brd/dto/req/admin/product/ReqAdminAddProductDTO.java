package com.zzy.brd.dto.req.admin.product;

import java.math.BigDecimal;

/**
 * 订单添加
 * @author lzh 2016/10/20
 *
 */
public class ReqAdminAddProductDTO {
	/** 产品名称 */
	private String productName;
	/** 产品类型 */
	private short productTypeId;
	/** 抵押方式*/
	private short mortgageType;
	/** 利息模式*/
	private short interestType;
	
	private BigDecimal spread;
	private BigDecimal spreadMin;
	private BigDecimal spreadMax;
	/** 日息/月息低价*/
	private BigDecimal expense;
	/** 提成比例*/
	private BigDecimal percentageRate;
	
	/***会员佣金比例*/
	private BigDecimal userRate;
	/** 师父佣金比率 */
	private BigDecimal fatherRate;
	/** 商家佣金比例*/
	private BigDecimal sellerRate;
	/** 业务员佣金比例*/
	private BigDecimal salesmanRate;
	/**佣金比例说明*/
	private String userRateDesc;
	private String fatherRateDesc;
	
	/**贷款利率*/
	private BigDecimal loanRate;
	/** 贷款页面利率-小*/
	private String loanMinRate;
	/** 贷款页面利率-大*/
	private String loanMaxRate;
	
	/** 贷款最小金额 */
	private BigDecimal loanMinAmount;
	/** 贷款最大金额 */
	private BigDecimal loanMaxAmount;
	/** 贷款最小期限 */
	private int loanMinTime;
	/** 贷款最大期限 */
	private int loanMaxTime;
	/** 还款方式 */
	private String repayment;
	/**申请条件*/
	private String requirment;
	/**材料准备*/
	private String materials;
	
	/** 是否推荐 */
	private short isDisplay;
	/** 产品描述 */
	private String productDesc;
	/** 算法参数A */
	private BigDecimal algoParamA;
	/** 算法参数B */
	private BigDecimal algoParamB;
	/** 算法参数C */
	private BigDecimal algoParamC;
	/** 算法参数D */
	private BigDecimal algoParamD;
	/** 算法参数E */
	private BigDecimal algoParamE;
	/** 算法参数F */
	private BigDecimal algoParamF;
	/** 算法参数G */
	private BigDecimal algoParamG;
	/** 算法参数H */
	private BigDecimal algoParamH;
	
	/** 申请次数*/
	private int applyTimes;
	/** 排序*/
	private int sortid;
	/** 首页排序*/
	private int indexSortid;
	
	/** 预计佣金发放方式*/
	private String brokerageSendDesc;
	
	/**前端佣金比例文字 */
	private String fontBrokerageDesc;
	private String fontBrokerageNum;
	
	public Long productId;
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public short getProductTypeId() {
		return productTypeId;
	}
	public void setProductTypeId(short productTypeId) {
		this.productTypeId = productTypeId;
	}
	public short getMortgageType() {
		return mortgageType;
	}
	public void setMortgageType(short mortgageType) {
		this.mortgageType = mortgageType;
	}
	public short getInterestType() {
		return interestType;
	}
	public void setInterestType(short interestType) {
		this.interestType = interestType;
	}
	public BigDecimal getExpense() {
		return expense;
	}
	public void setExpense(BigDecimal expense) {
		this.expense = expense;
	}
	public BigDecimal getSpread() {
		return spread;
	}
	public void setSpread(BigDecimal spread) {
		this.spread = spread;
	}
	public BigDecimal getSpreadMin() {
		return spreadMin;
	}
	public void setSpreadMin(BigDecimal spreadMin) {
		this.spreadMin = spreadMin;
	}
	public BigDecimal getSpreadMax() {
		return spreadMax;
	}
	public void setSpreadMax(BigDecimal spreadMax) {
		this.spreadMax = spreadMax;
	}
	public BigDecimal getPercentageRate() {
		return percentageRate;
	}
	public void setPercentageRate(BigDecimal percentageRate) {
		this.percentageRate = percentageRate;
	}
	public BigDecimal getUserRate() {
		return userRate;
	}
	public void setUserRate(BigDecimal userRate) {
		this.userRate = userRate;
	}
	public BigDecimal getFatherRate() {
		return fatherRate;
	}
	public void setFatherRate(BigDecimal fatherRate) {
		this.fatherRate = fatherRate;
	}
	public BigDecimal getSellerRate() {
		return sellerRate;
	}
	public void setSellerRate(BigDecimal sellerRate) {
		this.sellerRate = sellerRate;
	}
	public BigDecimal getSalesmanRate() {
		return salesmanRate;
	}
	public void setSalesmanRate(BigDecimal salesmanRate) {
		this.salesmanRate = salesmanRate;
	}
	
	public BigDecimal getLoanRate() {
		return loanRate;
	}
	public void setLoanRate(BigDecimal loanRate) {
		this.loanRate = loanRate;
	}
	public String getLoanMinRate() {
		return loanMinRate;
	}
	public void setLoanMinRate(String loanMinRate) {
		this.loanMinRate = loanMinRate;
	}
	public String getLoanMaxRate() {
		return loanMaxRate;
	}
	public void setLoanMaxRate(String loanMaxRate) {
		this.loanMaxRate = loanMaxRate;
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
	public String getRepayment() {
		return repayment;
	}
	public void setRepayment(String repayment) {
		this.repayment = repayment;
	}
	public String getRequirment() {
		return requirment;
	}
	public void setRequirment(String requirment) {
		this.requirment = requirment;
	}
	public String getMaterials() {
		return materials;
	}
	public void setMaterials(String materials) {
		this.materials = materials;
	}
	
	public String getUserRateDesc() {
		return userRateDesc;
	}
	public void setUserRateDesc(String userRateDesc) {
		this.userRateDesc = userRateDesc;
	}
	public String getFatherRateDesc() {
		return fatherRateDesc;
	}
	public void setFatherRateDesc(String fatherRateDesc) {
		this.fatherRateDesc = fatherRateDesc;
	}
	public short getIsDisplay() {
		return isDisplay;
	}
	public void setIsDisplay(short isDisplay) {
		this.isDisplay = isDisplay;
	}
	public String getProductDesc() {
		return productDesc;
	}
	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}
	public BigDecimal getAlgoParamA() {
		return algoParamA;
	}
	public void setAlgoParamA(BigDecimal algoParamA) {
		this.algoParamA = algoParamA;
	}
	public BigDecimal getAlgoParamB() {
		return algoParamB;
	}
	public void setAlgoParamB(BigDecimal algoParamB) {
		this.algoParamB = algoParamB;
	}
	public BigDecimal getAlgoParamC() {
		return algoParamC;
	}
	public void setAlgoParamC(BigDecimal algoParamC) {
		this.algoParamC = algoParamC;
	}
	public BigDecimal getAlgoParamD() {
		return algoParamD;
	}
	public void setAlgoParamD(BigDecimal algoParamD) {
		this.algoParamD = algoParamD;
	}
	public BigDecimal getAlgoParamE() {
		return algoParamE;
	}
	public void setAlgoParamE(BigDecimal algoParamE) {
		this.algoParamE = algoParamE;
	}
	public BigDecimal getAlgoParamF() {
		return algoParamF;
	}
	public void setAlgoParamF(BigDecimal algoParamF) {
		this.algoParamF = algoParamF;
	}
	public BigDecimal getAlgoParamG() {
		return algoParamG;
	}
	public void setAlgoParamG(BigDecimal algoParamG) {
		this.algoParamG = algoParamG;
	}
	public BigDecimal getAlgoParamH() {
		return algoParamH;
	}
	public void setAlgoParamH(BigDecimal algoParamH) {
		this.algoParamH = algoParamH;
	}
	public int getApplyTimes() {
		return applyTimes;
	}
	public void setApplyTimes(int applyTimes) {
		this.applyTimes = applyTimes;
	}
	public int getSortid() {
		return sortid;
	}
	public void setSortid(int sortid) {
		this.sortid = sortid;
	}
	public int getIndexSortid() {
		return indexSortid;
	}
	public void setIndexSortid(int indexSortid) {
		this.indexSortid = indexSortid;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public String getBrokerageSendDesc() {
		return brokerageSendDesc;
	}
	public void setBrokerageSendDesc(String brokerageSendDesc) {
		this.brokerageSendDesc = brokerageSendDesc;
	}
	public String getFontBrokerageDesc() {
		return fontBrokerageDesc;
	}
	public void setFontBrokerageDesc(String fontBrokerageDesc) {
		this.fontBrokerageDesc = fontBrokerageDesc;
	}
	public String getFontBrokerageNum() {
		return fontBrokerageNum;
	}
	public void setFontBrokerageNum(String fontBrokerageNum) {
		this.fontBrokerageNum = fontBrokerageNum;
	}
	
}
