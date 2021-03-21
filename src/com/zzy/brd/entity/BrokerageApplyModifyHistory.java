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

import com.zzy.brd.entity.interfaces.IdEntity;

/**
 * @author:xpk
 *    2016年9月26日-下午5:15:14
 **/
@Entity
@Table(name = "brokerage_apply_modify_history")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate(value = true)
public class BrokerageApplyModifyHistory extends IdEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5451259429617352519L;
	
	@ManyToOne(targetEntity=User.class,fetch=FetchType.LAZY)
	@JoinColumn(name="oper_id",referencedColumnName="id")
	private User oper;
	
	@ManyToOne(targetEntity=BrokerageApply.class,fetch=FetchType.LAZY)
	@JoinColumn(name="brokerage_apply_id",referencedColumnName="id")
	private BrokerageApply brokerageApply;
	
	private String content;
	
	private Date addDate;
	
	public User getOper() {
		return oper;
	}

	public void setOper(User oper) {
		this.oper = oper;
	}

	public BrokerageApply getBrokerageApply() {
		return brokerageApply;
	}

	public void setBrokerageApply(BrokerageApply brokerageApply) {
		this.brokerageApply = brokerageApply;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getAddDate() {
		return addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}	
}
