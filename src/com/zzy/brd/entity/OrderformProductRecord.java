package com.zzy.brd.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicUpdate;

import com.zzy.brd.entity.interfaces.IdEntity;


/**
 * @author:xpk
 *    2016年9月26日-下午2:01:16
 **/
@Entity
@Table(name = "orderform_product_record")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate(value = true)
public class OrderformProductRecord extends IdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8833794516828292001L;
	
	@ManyToOne(targetEntity=Orderform.class,fetch=FetchType.LAZY)
	@JoinColumn(name="orderform_id",referencedColumnName="id")
	private Orderform orderform;
	
	private Date createTime;
	
	@OneToOne(targetEntity=OrderOperLog.class,mappedBy="productChangeRecord")
	private OrderOperLog orderOperLog;
	
	@ManyToOne(targetEntity=Product.class,fetch=FetchType.LAZY)
	@JoinColumn(name="before_product",referencedColumnName="id")
	private Product beforeProduct;
	
	@ManyToOne(targetEntity=Product.class,fetch=FetchType.LAZY)
	@JoinColumn(name="after_product",referencedColumnName="id")
	private Product afterProduct;
	
	/***贷款之前的信息保存**/
	private BigDecimal money ;
	
	private int month;
	
	private BigDecimal loanInterestRate;
	
	/** 产品加息息差值*/
	private BigDecimal spreadRate;
	
	public Orderform getOrderform() {
		return orderform;
	}

	public void setOrderform(Orderform orderform) {
		this.orderform = orderform;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public OrderOperLog getOrderOperLog() {
		return orderOperLog;
	}

	public void setOrderOperLog(OrderOperLog orderOperLog) {
		this.orderOperLog = orderOperLog;
	}

	public Product getBeforeProduct() {
		return beforeProduct;
	}

	public void setBeforeProduct(Product beforeProduct) {
		this.beforeProduct = beforeProduct;
	}

	public Product getAfterProduct() {
		return afterProduct;
	}

	public void setAfterProduct(Product afterProduct) {
		this.afterProduct = afterProduct;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public BigDecimal getLoanInterestRate() {
		return loanInterestRate;
	}

	public void setLoanInterestRate(BigDecimal loanInterestRate) {
		this.loanInterestRate = loanInterestRate;
	}

	public BigDecimal getSpreadRate() {
		return spreadRate;
	}

	public void setSpreadRate(BigDecimal spreadRate) {
		this.spreadRate = spreadRate;
	}
}	
