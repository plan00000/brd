package com.zzy.brd.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicUpdate;

import com.zzy.brd.entity.interfaces.IdEntity;

/**
 * @author:xpk
 *    2016年9月26日-下午2:12:56
 **/
@Entity
@Table(name = "product_info")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate(value = true)
public class ProductInfo extends IdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7926020265112573611L;
	
	private String productName;
	
	/**贷款利率*/
	private BigDecimal loanRate;
	
	/** 贷款页面利率-小*/
	private String loanMinRate;
	
	/** 贷款页面利率-大*/
	private String loanMaxRate;
	/**贷款金额*/
	private BigDecimal loanMinAmount;
	
	private BigDecimal loanMaxAmount;
	
	/**贷款期限*/
	private int loanMinTime;
	
	private int loanMaxTime;
	
	/**还款方式*/
	private String repayment;
	
	/**产品信息*/
	private String productDesc;
	
	/**申请条件*/
	private String requirment;
	
	/**材料准备*/
	private String materials;
	
	/** 日息低价 月息低价*/
	private BigDecimal spread;
	private BigDecimal spreadMin;
	private BigDecimal spreadMax;
	
	/**手续费用*/
	private BigDecimal expense;
	/** 提成比例*/
	private BigDecimal percentageRate;
	
	/***会员佣金比例*/
	private BigDecimal userRate;
	
	/**师傅佣金比例*/
	private BigDecimal fatherRate;
	
	/**商家佣金比例*/
	private BigDecimal sellerRate;
	
	/**业务员佣金比例*/
	private BigDecimal salesmanRate;
	
	/**佣金比例说明*/
	private String userRateDesc;
	private String fatherRateDesc;
	
	/**前端佣金比例文字 */
	private String fontBrokerageDesc;
	private String fontBrokerageNum;
	/** 前端佣金数值*/
	/**算法参数*/
	@Column(name="algo_param_a")
	private BigDecimal algoParamA;
	@Column(name="algo_param_b")
	private BigDecimal algoParamB;
	@Column(name="algo_param_c")
	private BigDecimal algoParamC;
	@Column(name="algo_param_d")
	private BigDecimal algoParamD;
	@Column(name="algo_param_e")
	private BigDecimal algoParamE;
	@Column(name="algo_param_f")
	private BigDecimal algoParamF;
	@Column(name="algo_param_g")
	private BigDecimal algoParamG;
	@Column(name="algo_param_h")
	private BigDecimal algoParamH;
	//预计佣金发放方式
	private String brokerageSendDesc;
	@OneToOne(targetEntity=Product.class,mappedBy="info")
	private Product product;
	
	/*@Override
	public void prepareForInsert() {
		this.spread = new BigDecimal(0);
		this.spreadMin = new BigDecimal(0);
		this.spreadMax = new BigDecimal(0);
		this.expense = new BigDecimal(0);
		this.percentageRate = new BigDecimal(0);
		this.userRate = new BigDecimal(0);
		this.fatherRate = new BigDecimal(0);
		this.sellerRate = new BigDecimal(0);
		this.salesmanRate = new BigDecimal(0);
		this.algoParamA = new BigDecimal(0);
		this.algoParamB = new BigDecimal(0);
		this.algoParamC = new BigDecimal(0);
		this.algoParamD = new BigDecimal(0);
		this.algoParamE = new BigDecimal(0);
		this.algoParamF = new BigDecimal(0);
		this.algoParamG = new BigDecimal(0);
		this.algoParamH = new BigDecimal(0);
	}*/
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
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

	public String getProductDesc() {
		return productDesc;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}

	public String getMaterials() {
		return materials;
	}

	public void setMaterials(String materials) {
		this.materials = materials;
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

	public String getRequirment() {
		return requirment;
	}

	public void setRequirment(String requirment) {
		this.requirment = requirment;
	}

	public BigDecimal getPercentageRate() {
		return percentageRate;
	}

	public void setPercentageRate(BigDecimal percentageRate) {
		this.percentageRate = percentageRate;
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
