package com.zzy.brd.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicUpdate;

import com.zzy.brd.entity.Orderform.OrderformStatus;
import com.zzy.brd.entity.interfaces.IdEntity;

/**
 * @author:xpk
 *    2016年9月26日-上午11:52:26
 **/
@Entity
@Table(name = "orderform_remark")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate(value = true)
public class OrderformRemark extends IdEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1528765336823611016L;
		
	@ManyToOne(targetEntity=Orderform.class,fetch=FetchType.LAZY)
	@JoinColumn(name="orderform_id",referencedColumnName="id")
	private Orderform orderform;
	
	@ManyToOne(targetEntity=User.class,fetch=FetchType.LAZY)
	@JoinColumn(name="user_id",referencedColumnName="id")
	private User user;
	
	private String remark;
	
	private Date createTime;

	public Orderform getOrderform() {
		return orderform;
	}

	public void setOrderform(Orderform orderform) {
		this.orderform = orderform;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}	
