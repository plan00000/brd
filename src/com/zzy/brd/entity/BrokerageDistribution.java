package com.zzy.brd.entity;

import java.math.BigDecimal;
import java.util.Date;

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
 *    2016年9月26日-下午5:53:21
 **/
@Entity
@Table(name = "brokerage_distribution")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate(value = true)
public class BrokerageDistribution extends IdEntity {
	
	public enum BrokerageType {
		SELF,
		FATHER,
		GRANDFATHER,
		;		
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5855851536606926282L;
	
	@ManyToOne(targetEntity=Orderform.class,fetch=FetchType.LAZY)
	@JoinColumn(name="orderform_id",referencedColumnName="id")
	private Orderform orderform;
	
	private BigDecimal brokerage;
	
	private BrokerageType brokerageType;
	
	private Date addDate;
	
	@ManyToOne(targetEntity=User.class,fetch=FetchType.LAZY)
	@JoinColumn(name="operater",referencedColumnName="id")
	private User operater;
	
	@ManyToOne(targetEntity=User.class,fetch=FetchType.LAZY)
	@JoinColumn(name="userid",referencedColumnName="id")
	private User user;
	
	public Orderform getOrderform() {
		return orderform;
	}

	public void setOrderform(Orderform orderform) {
		this.orderform = orderform;
	}

	public BigDecimal getBrokerage() {
		return brokerage;
	}

	public void setBrokerage(BigDecimal brokerage) {
		this.brokerage = brokerage;
	}

	public BrokerageType getBrokerageType() {
		return brokerageType;
	}

	public void setBrokerageType(BrokerageType brokerageType) {
		this.brokerageType = brokerageType;
	}

	public Date getAddDate() {
		return addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}

	public User getOperater() {
		return operater;
	}

	public void setOperater(User operater) {
		this.operater = operater;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
