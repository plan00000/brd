package com.zzy.brd.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicUpdate;

import com.zzy.brd.entity.interfaces.IdEntity;

/**
 * @author:xpk
 *    2016年9月26日-下午2:08:57
 **/
@Entity
@Table(name = "product")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate(value = true)
public class Product extends IdEntity {
	
	public enum MortgageType {
		NULLLOAN, /** 无抵押贷*/
		MORTGAGELOAN, /**抵押贷*/	
		CREDITLOAN, /**信用贷*/
		;
		public String getDes() {
			if(this == NULLLOAN){
				return "无抵押贷"; 
			}
			if(this==MORTGAGELOAN){
				return "抵押贷";
			}
			if(this==CREDITLOAN){
				return "信用贷";
			}
			return "";
		}		
	}	
	
	public enum InterestType {
		/**0-利息模式按月1-利息模式按日2-手续费、收益金模式3-特殊模式*/
		INTERESTMODELMONTH, 
		INTERESTMODELDAY,
		HANDFEEMODEL,
		SPECIALMODEL;
		public String getDes() {
			if(this == INTERESTMODELMONTH){
				return "利息按月"; 
			}
			if(this==INTERESTMODELDAY){
				return "利息按日";
			}
			if(this==HANDFEEMODEL){
				return "收益金";
			}
			return "特殊模式";
		}	
	}
	
	public enum isDisplay {
		NO,
		YES,
		;
		public String getDes(){
			if(this == NO)
				return "取消推荐";
			if(this ==YES)
				return "推荐";
			return "";
		}
	}
	
	public enum Status {
		NORMAL, /**未删除*/
		DELETED, /**已删除*/
		;
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5936760764750807772L;
	
	/** 产品类型*/
	@ManyToOne(targetEntity=ProductType.class,fetch= FetchType.LAZY)
	@JoinColumn(name="product_type_id",referencedColumnName="id")
	private ProductType type;
	
	/** 产品详情*/
	@OneToOne(targetEntity=ProductInfo.class,cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	@JoinColumn(name="product_info_id",referencedColumnName="id")
	private ProductInfo info ;
	
	/** 抵押方式*/
	@Column(name="mortgage_type")
	private MortgageType mortgageType;
	
	/** 利息模式*/
	@Column(name="interest_type")
	private InterestType interestType;

	/** 添加时间*/
	private Date createTime;
	
	/** 是否推荐到首页*/
	@Column(name="is_display")
	private isDisplay isDisplay;
	
	/** 是否已删除*/
	private Status status;
	
	/** 申请次数*/
	private int applyTimes;
	
	/** 排序*/
	private int sortid;
	/** 首页排序*/
	private int indexSortid;
	/** 初始化*/
	
	public ProductType getType() {
		return type;
	}

	public void setType(ProductType type) {
		this.type = type;
	}

	public ProductInfo getInfo() {
		return info;
	}

	public void setInfo(ProductInfo info) {
		this.info = info;
	}

	public MortgageType getMortgageType() {
		return mortgageType;
	}

	public void setMortgageType(MortgageType mortgageType) {
		this.mortgageType = mortgageType;
	}

	public InterestType getInterestType() {
		return interestType;
	}

	public void setInterestType(InterestType interestType) {
		this.interestType = interestType;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public isDisplay getIsDisplay() {
		return isDisplay;
	}

	public void setIsDisplay(isDisplay isDisplay) {
		this.isDisplay = isDisplay;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public int getApplyTimes() {
		return applyTimes;
	}

	public void setApplyTimes(int applyTimes) {
		this.applyTimes = applyTimes;
	}

	public int getSortid() {
		return sortid;
	}

	public void setSortid(int sortid) {
		this.sortid = sortid;
	}

	public int getIndexSortid() {
		return indexSortid;
	}

	public void setIndexSortid(int indexSortid) {
		this.indexSortid = indexSortid;
	}
}
