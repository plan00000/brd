package com.zzy.brd.dto.req.pc.orderform;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

/**
 * pc-订单提交
 * @author lzh 2016-10-9
 *
 */
public class ReqPcOrderformDTO {
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
	/** 验证码*/
	@NotNull(message = "手机验证码不能为空")
	private String phoneAuthcode;
	/** 贷款佣金*/
	@NotNull(message = "日息不能为空")
	private BigDecimal brokerageRate;
	/** 贷款产品id*/
	private long productId;
	/** 备注*/
	private String remark;
	
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
	public String getPhoneAuthcode() {
		return phoneAuthcode;
	}
	public void setPhoneAuthcode(String phoneAuthcode) {
		this.phoneAuthcode = phoneAuthcode;
	}
	public BigDecimal getBrokerageRate() {
		return brokerageRate;
	}
	public void setBrokerageRate(BigDecimal brokerageRate) {
		this.brokerageRate = brokerageRate;
	}
	public long getProductId() {
		return productId;
	}
	public void setProductId(long productId) {
		this.productId = productId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
