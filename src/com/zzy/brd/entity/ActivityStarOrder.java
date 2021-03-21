package com.zzy.brd.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicUpdate;

import com.zzy.brd.entity.interfaces.IdEntity;

/**
 * @author:xpk
 *    2016年9月22日-上午10:47:02
 **/
@Entity
@Table(name = "activity_star_order")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate(value = true)
public class ActivityStarOrder extends IdEntity {
	
	public enum Status {
		UNCOMPLETE, /**未完成*/
		COMPLETE, /**完成*/
		;
		public String getDes() {
			if(this==UNCOMPLETE)
				return "未完成";
			if(this == COMPLETE)
				return "完成";
			return "";
		}
		
	}
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6834094979967892668L;
	
	@OneToOne(targetEntity=User.class,fetch=FetchType.LAZY)
	@JoinColumn(referencedColumnName="id",name="user_id")
	private User user; 
	
	private int orderSum;
	
	private Date startTime;
	
	private Date endTime;
	
	private Status status;
	

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getOrderSum() {
		return orderSum;
	}

	public void setOrderSum(int orderSum) {
		this.orderSum = orderSum;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}	
}
