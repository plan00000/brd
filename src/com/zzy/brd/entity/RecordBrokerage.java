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
 * 获取的佣金记录表
 * @author:xpk
 *    2016年9月22日-下午5:33:58
 **/
@Entity
@Table(name = "record_brokerage")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate(value = true)
public class RecordBrokerage extends IdEntity {
	
	public enum RelationType {
		/**本人*/
		SELF, 
		/**徒弟*/
		APPRENTICE, 
		/**徒孙*/
		DISCIPLE, 
		/**徒孙孙*/
		GGSON,
		;
		public String getDes(){
			if(this==SELF){
				return "本人";
			}
			if(this==APPRENTICE){
				return "徒弟";
			}
			if(this==DISCIPLE){
				return "徒孙";
			}
			if(this==GGSON){
				return "徒孙孙";
			}
			return "";			
		}
		
		
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4077915175455404997L;
	/** 佣金获得者*/
	@ManyToOne(targetEntity=User.class,fetch=FetchType.LAZY)
	@JoinColumn(name="user_id",referencedColumnName="id")
	private User user;
	/** 提单人*/
	@ManyToOne(targetEntity = User.class,fetch = FetchType.LAZY)
	@JoinColumn(name="gain_user_id",referencedColumnName = "id")
	private User gainUser;

	/** 订单*/
	@ManyToOne(targetEntity=Orderform.class,fetch=FetchType.LAZY)
	@JoinColumn(name="order_id",referencedColumnName="id")
	private Orderform order;
	
	/**确认人*/
	private String confirmName;
	/** 关系*/
	
	@Column(name="relate")
	private RelationType relate;
	/** 抽佣比例*/
	private BigDecimal haveBrokerageRate;
	/** 获得佣金金额*/
	private BigDecimal haveBrokerage; 
	/** 发佣时间*/
	private Date sendBrokerage;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getGainUser() {
		return gainUser;
	}

	public void setGainUser(User gainUser) {
		this.gainUser = gainUser;
	}

	public Orderform getOrder() {
		return order;
	}

	public void setOrder(Orderform order) {
		this.order = order;
	}

	public String getConfirmName() {
		return confirmName;
	}

	public void setConfirmName(String confirmName) {
		this.confirmName = confirmName;
	}

	public RelationType getRelate() {
		return relate;
	}

	public void setRelate(RelationType relate) {
		this.relate = relate;
	}

	public BigDecimal getHaveBrokerageRate() {
		return haveBrokerageRate;
	}

	public void setHaveBrokerageRate(BigDecimal haveBrokerageRate) {
		this.haveBrokerageRate = haveBrokerageRate;
	}

	public BigDecimal getHaveBrokerage() {
		return haveBrokerage;
	}

	public void setHaveBrokerage(BigDecimal haveBrokerage) {
		this.haveBrokerage = haveBrokerage;
	}

	public Date getSendBrokerage() {
		return sendBrokerage;
	}

	public void setSendBrokerage(Date sendBrokerage) {
		this.sendBrokerage = sendBrokerage;
	}

	}
