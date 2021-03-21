package com.zzy.brd.mobile.web.dto.rep.orderform;

import java.math.BigDecimal;

import com.zzy.brd.entity.Orderform;

/**
 * @author:xpk
 *    2016年9月28日-下午2:43:09
 **/
public class RepMyLoanDataDTO {
	
	private long id; 
	
	private String productType;
	
	private String productName;
	
	private BigDecimal money;
	
	private String name;
	
	private BigDecimal brokerage;
	
	private String statusStr;
	
	
	public RepMyLoanDataDTO(){
		
	}
	
	public RepMyLoanDataDTO(Orderform orderform){
		this.id = orderform.getId();
		this.productName = orderform.getProductInfo().getProductName();
		this.productType = orderform.getProduct().getType().getBillType().getDes();
		if(orderform.getStatus().equals(Orderform.OrderformStatus.UNCONTACTED) || 
				orderform.getStatus().equals(Orderform.OrderformStatus.UNTOLKWITH) || 
				orderform.getStatus().equals(Orderform.OrderformStatus.UNCHECKED) || 
				orderform.getStatus().equals(Orderform.OrderformStatus.CHECKFAIL)  ){
			this.money = orderform.getMoney().divide(new BigDecimal("10000"));
		}else{
			this.money = orderform.getMoney().divide(new BigDecimal("10000"));
		}
		if(orderform.getName().length()<3){
			this.name = "*"+orderform.getName().substring(orderform.getName().length()-1,orderform.getName().length());
		} else {
			this.name = "**"+orderform.getName().substring(orderform.getName().length()-1,orderform.getName().length());
		}
		if(orderform.getBrokerageRateNum()==null){
			this.brokerage = orderform.getSelfBrokerageNum();
		}else {
			this.brokerage = orderform.getBrokerageRateNum();
		}
		this.statusStr = orderform.getStatus().getDes();		
		if(orderform.getStatus().equals(Orderform.OrderformStatus.UNCONTACTED) || orderform.getStatus().equals(Orderform.OrderformStatus.UNTOLKWITH)){
			this.statusStr = "待审核"; 
		}
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getBrokerage() {
		return brokerage;
	}

	public void setBrokerage(BigDecimal brokerage) {
		this.brokerage = brokerage;
	}

	public String getStatusStr() {
		return statusStr;
	}

	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}	
	
}
