package com.zzy.brd.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicUpdate;

import com.zzy.brd.entity.interfaces.IdEntity;

/**
 * 报表统计-订单统计
 * @author lzh 2016-9-23
 *
 */
@Entity
@Table(name = "order_common_statistics")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate(value = true)
public class OrderCommonStatistics extends IdEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4237716865049161964L;
	/** 每日提交贷款订单数*/
	private int OrderNum;
	/** 每日成交贷款订单数*/
	private int orderSuccessNum;
	/** 每日成交贷款订单金额*/
	private BigDecimal orderSuccessDecimal;
	/** 统计日期*/
	private Date statisticsDate;
	public int getOrderNum() {
		return OrderNum;
	}
	public void setOrderNum(int orderNum) {
		OrderNum = orderNum;
	}
	public int getOrderSuccessNum() {
		return orderSuccessNum;
	}
	public void setOrderSuccessNum(int orderSuccessNum) {
		this.orderSuccessNum = orderSuccessNum;
	}
	
	public BigDecimal getOrderSuccessDecimal() {
		return orderSuccessDecimal;
	}
	public void setOrderSuccessDecimal(BigDecimal orderSuccessDecimal) {
		this.orderSuccessDecimal = orderSuccessDecimal;
	}
	public Date getStatisticsDate() {
		return statisticsDate;
	}
	public void setStatisticsDate(Date statisticsDate) {
		this.statisticsDate = statisticsDate;
	}
	
}
