package com.zzy.brd.entity;




import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicUpdate;

import com.zzy.brd.entity.interfaces.IdEntity;

/**
 * @author:xpk
 *    2016年11月18日-下午3:10:54
 **/
@Entity
@Table(name = "brokerage_payment_records")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate(value = true)
public class BrokeragePaymentRecords extends IdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6740785312476907060L;
	
	/**对应的佣金订单**/
	@ManyToOne(targetEntity=BrokerageApply.class,fetch=FetchType.LAZY)
	@JoinColumn(name="brokerage_apply_id",referencedColumnName="id")
	private BrokerageApply brokerageApply;
	
	/***对应的订单**/
	@ManyToOne(targetEntity=Orderform.class,fetch=FetchType.LAZY)
	@JoinColumn(name="orderform_id",referencedColumnName="id")
	private Orderform orderform;
	
	/**本次是第几次发放**/
	private int number;
	
	private Date createTime;
	
	private String remark;
	
	/**提单人发放金额**/
	private BigDecimal selfPaymentBrokerage;
	
	/**师父**/
	private BigDecimal fatherPaymentBrokerage;
	
	/***商家**/
	private BigDecimal businessPaymentBrokerage;
	
	/***业务员*/
	private BigDecimal salesmanPaymentBrokerage;

	public BrokerageApply getBrokerageApply() {
		return brokerageApply;
	}

	public void setBrokerageApply(BrokerageApply brokerageApply) {
		this.brokerageApply = brokerageApply;
	}

	public Orderform getOrderform() {
		return orderform;
	}

	public void setOrderform(Orderform orderform) {
		this.orderform = orderform;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public BigDecimal getSelfPaymentBrokerage() {
		return selfPaymentBrokerage;
	}

	public void setSelfPaymentBrokerage(BigDecimal selfPaymentBrokerage) {
		this.selfPaymentBrokerage = selfPaymentBrokerage;
	}

	public BigDecimal getFatherPaymentBrokerage() {
		return fatherPaymentBrokerage;
	}

	public void setFatherPaymentBrokerage(BigDecimal fatherPaymentBrokerage) {
		this.fatherPaymentBrokerage = fatherPaymentBrokerage;
	}

	public BigDecimal getBusinessPaymentBrokerage() {
		return businessPaymentBrokerage;
	}

	public void setBusinessPaymentBrokerage(BigDecimal businessPaymentBrokerage) {
		this.businessPaymentBrokerage = businessPaymentBrokerage;
	}

	public BigDecimal getSalesmanPaymentBrokerage() {
		return salesmanPaymentBrokerage;
	}

	public void setSalesmanPaymentBrokerage(BigDecimal salesmanPaymentBrokerage) {
		this.salesmanPaymentBrokerage = salesmanPaymentBrokerage;
	}

}
