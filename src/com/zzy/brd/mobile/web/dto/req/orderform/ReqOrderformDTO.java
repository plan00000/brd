package com.zzy.brd.mobile.web.dto.req.orderform;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

/**
 * 贷款申请dto
 * @author lzh 2016-9-29
 *
 */
public class ReqOrderformDTO {
	
	/** 贷款人*/
	@NotNull(message = "姓名不能为空")
	private String name;
	/** 身份证后6位*/
	@NotNull(message = "身份证不能为空")
	private String idcard;
	/** 手机号*/
	@NotNull(message = "手机号不能为空")
	private String telephone;
	/** 贷款金额*/
	@NotNull(message = "贷款金额不能为空")
	private BigDecimal money;
	/** 贷款期限*/
	@NotNull(message = "贷款期限不能为空")
	private int loanTime;
	/** 省*/
	@NotNull
	private String province;
	/** 城市*/
	@NotNull
	private String city;
	/** 贷款佣金日息息差*/
	private BigDecimal spreadRate;
	/** 预计佣金*/
	private BigDecimal brokerageRateNum;
	/** 订单备注*/
	private String remark;
	/** 贷款产品id*/
	private long productId;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIdcard() {
		return idcard;
	}
	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public BigDecimal getMoney() {
		return money;
	}
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	public int getLoanTime() {
		return loanTime;
	}
	public void setLoanTime(int loanTime) {
		this.loanTime = loanTime;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public BigDecimal getSpreadRate() {
		return spreadRate;
	}
	public void setSpreadRate(BigDecimal spreadRate) {
		this.spreadRate = spreadRate;
	}
	public BigDecimal getBrokerageRateNum() {
		return brokerageRateNum;
	}
	public void setBrokerageRateNum(BigDecimal brokerageRateNum) {
		this.brokerageRateNum = brokerageRateNum;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public long getProductId() {
		return productId;
	}
	public void setProductId(long productId) {
		this.productId = productId;
	}
	
	
}
