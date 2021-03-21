package com.zzy.brd.entity;

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
 * 用户每日数据统计
 * @author:xpk
 *    2016年10月21日-上午11:50:41
 **/
@Entity
@Table(name = "user_daily_statistics")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate(value = true)
public class UserDailyStatistics extends IdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5554112858354451739L;
	
	
	@ManyToOne(targetEntity= User.class,fetch=FetchType.LAZY)
	@JoinColumn(name="user_id",referencedColumnName="id")
	private User user;
	
	/***徒弟数量*/
	private int sonSum;
	
	/**徒孙数量*/
	private int grandSonSum;
	
	/**徒孙孙数量*/
	private int ggrandSonSum;
	
	/***订单数量*/
	private int orderSum;
	
	/**成功订单数**/
	private int orderSuccessSum;
	
	/***统计时间*/
	private Date statisticsDate;
	
	/***登录次数*/
	@Column(name="logintimes")
	private int loginTimes;
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getSonSum() {
		return sonSum;
	}

	public void setSonSum(int sonSum) {
		this.sonSum = sonSum;
	}

	public int getGrandSonSum() {
		return grandSonSum;
	}

	public void setGrandSonSum(int grandSonSum) {
		this.grandSonSum = grandSonSum;
	}

	public int getGgrandSonSum() {
		return ggrandSonSum;
	}

	public void setGgrandSonSum(int ggrandSonSum) {
		this.ggrandSonSum = ggrandSonSum;
	}

	public int getOrderSum() {
		return orderSum;
	}

	public void setOrderSum(int orderSum) {
		this.orderSum = orderSum;
	}

	public int getOrderSuccessSum() {
		return orderSuccessSum;
	}

	public void setOrderSuccessSum(int orderSuccessSum) {
		this.orderSuccessSum = orderSuccessSum;
	}

	public Date getStatisticsDate() {
		return statisticsDate;
	}

	public void setStatisticsDate(Date statisticsDate) {
		this.statisticsDate = statisticsDate;
	}

	public int getLoginTimes() {
		return loginTimes;
	}

	public void setLoginTimes(int loginTimes) {
		this.loginTimes = loginTimes;
	}	
	
}
