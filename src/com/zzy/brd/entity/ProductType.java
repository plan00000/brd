package com.zzy.brd.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicUpdate;

import com.zzy.brd.entity.interfaces.IdEntity;

/**
 * @author:xpk
 *    2016年9月23日-上午9:55:36
 **/
@Entity
@Table(name = "product_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate(value = true)
public class ProductType extends IdEntity {
	
	/**提单类型*/
	public enum BillType {
		/**自助贷*/
		SELFHELPLOAN, 
		/**赚差价*/
		EARNDIFFERENCE, 
		/**赚提成*/
		EARNCOMMISSION, 
		;
		public String getDes(){
			if(this == SELFHELPLOAN)
				return "自助贷";
			if(this == EARNDIFFERENCE)
				return "赚差价";
			if(this == EARNCOMMISSION)
				return "赚提成";
			return "";	
		}
	}
	/** */
	public enum State{
		DEL,/**已删除*/
		NORMAL,/**正常*/
		;
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5158780489681485227L;
	
	/**提单类型*/
	private BillType billType;
	
	/**产品名称*/
	private String productName;
	
	/**创建时间*/
	private Date createTime;
	
	/** 是否已删除*/
	private State state;

	public BillType getBillType() {
		return billType;
	}

	public void setBillType(BillType billType) {
		this.billType = billType;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}
	
	
}
