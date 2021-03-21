package com.zzy.brd.entity;

import java.math.BigDecimal;

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
 *    2016年9月27日-上午9:11:38
 **/
@Entity
@Table(name = "user_department_statistics")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate(value = true)
public class UserDepartmentStatistics extends IdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7357284689878523690L;
	
	@ManyToOne(targetEntity=Department.class,fetch=FetchType.LAZY)
	@JoinColumn(name="department_id",referencedColumnName="id")
	private Department department;
	
	private int userNum;
	
	private int salesmanNum;
	
	private int managerNum;
	
	private int ordersNum;
	
	private BigDecimal orderAmount;
	
	private int yesterdayOrdernum;
	
	private int yesterdayOrderamount;
	
	private BigDecimal paymentAmount;

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public int getUserNum() {
		return userNum;
	}

	public void setUserNum(int userNum) {
		this.userNum = userNum;
	}

	public int getSalesmanNum() {
		return salesmanNum;
	}

	public void setSalesmanNum(int salesmanNum) {
		this.salesmanNum = salesmanNum;
	}

	public int getManagerNum() {
		return managerNum;
	}

	public void setManagerNum(int managerNum) {
		this.managerNum = managerNum;
	}

	public int getOrdersNum() {
		return ordersNum;
	}

	public void setOrdersNum(int ordersNum) {
		this.ordersNum = ordersNum;
	}

	public BigDecimal getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(BigDecimal orderAmount) {
		this.orderAmount = orderAmount;
	}

	public int getYesterdayOrdernum() {
		return yesterdayOrdernum;
	}

	public void setYesterdayOrdernum(int yesterdayOrdernum) {
		this.yesterdayOrdernum = yesterdayOrdernum;
	}

	public int getYesterdayOrderamount() {
		return yesterdayOrderamount;
	}

	public void setYesterdayOrderamount(int yesterdayOrderamount) {
		this.yesterdayOrderamount = yesterdayOrderamount;
	}

	public BigDecimal getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(BigDecimal paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
}
