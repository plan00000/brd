package com.zzy.brd.dto.rep.product;

import java.math.BigDecimal;

import com.zzy.brd.entity.Product;
import com.zzy.brd.util.tld.NumberUtils;

/***
 * 用于首页的‘产品介绍获取’
 * 
 * @author 
 *
 */
public class RepDisplayProductDTO {
	/** 产品名称 */
	private String productName;
	private Long productId;
	private BigDecimal fatherRate = BigDecimal.ZERO;
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
	/**贷款利率*/
	private String fatherRateStr;
	
	
	public RepDisplayProductDTO(Product product) {
		this.productName = product.getInfo().getProductName();
		this.productId = product.getId();
		BigDecimal fatherRate = product.getInfo().getFatherRate();
		if(fatherRate ==null){
			fatherRate = BigDecimal.ZERO;
		}
		this.loanMinAmount =product.getInfo().getLoanMinAmount();
		this.loanMaxAmount = product.getInfo().getLoanMaxAmount();
		this.loanMinTime =product.getInfo().getLoanMinTime();
		this.loanMaxTime = product.getInfo().getLoanMaxTime();
		String loanMaxRate = product.getInfo().getLoanMaxRate();
		String loanMinRate = product.getInfo().getLoanMinRate();
		this.loanRate = loanMinRate+"%~"+loanMaxRate+"%";
		//this.fatherRateStr  = NumberUtils.hundredPercentNumber1(fatherRate);//取小数点为一位的百分数
		this.fatherRateStr  = fatherRate.toString();//取小数点为一位的百分数
	}


	public String getProductName() {
		return productName;
	}


	public void setProductName(String productName) {
		this.productName = productName;
	}


	public Long getProductId() {
		return productId;
	}


	public void setProductId(Long productId) {
		this.productId = productId;
	}


	public BigDecimal getFatherRate() {
		return fatherRate;
	}


	public void setFatherRate(BigDecimal fatherRate) {
		this.fatherRate = fatherRate;
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


	public String getFatherRateStr() {
		return fatherRateStr;
	}


	public void setFatherRateStr(String fatherRateStr) {
		this.fatherRateStr = fatherRateStr;
	}

	
}
