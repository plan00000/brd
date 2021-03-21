package com.zzy.brd.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicUpdate;

import com.zzy.brd.entity.interfaces.IdEntity;

/**
 * 报表统计 每日统计 包括会员统计 订单统计
 * @author lzh 2016-9-23
 *
 */
@Entity
@Table(name = "user_common_statistics")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate(value = true)
public class UserCommonStatistics extends IdEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1472726995367301969L;
	/** 统计类型*/
	public enum StatisticsType{
		
	}
	/** 每日注册会员数*/
	private int registerNum;
	/** 每日登录会员数*/
	private int loginNum;
	/** 昨日新增会员*/
	private int yesterdayUser;
	/** 昨日新增融资经理*/
	private int yesterdayManager;
	/** 昨日新增商家*/
	private int yesterdaySeller;
	/**所有会员总数*/
	private int alluserNum;
	
	/** 统计时间(保存到日)*/
	private Date statisticsDate;
	
	public int getRegisterNum() {
		return registerNum;
	}
	public void setRegisterNum(int registerNum) {
		this.registerNum = registerNum;
	}
	public int getLoginNum() {
		return loginNum;
	}
	public void setLoginNum(int loginNum) {
		this.loginNum = loginNum;
	}
	public int getYesterdayUser() {
		return yesterdayUser;
	}
	public void setYesterdayUser(int yesterdayUser) {
		this.yesterdayUser = yesterdayUser;
	}
	public int getYesterdayManager() {
		return yesterdayManager;
	}
	public void setYesterdayManager(int yesterdayManager) {
		this.yesterdayManager = yesterdayManager;
	}
	public int getYesterdaySeller() {
		return yesterdaySeller;
	}
	public void setYesterdaySeller(int yesterdaySeller) {
		this.yesterdaySeller = yesterdaySeller;
	}
	public Date getStatisticsDate() {
		return statisticsDate;
	}
	public void setStatisticsDate(Date statisticsDate) {
		this.statisticsDate = statisticsDate;
	}
	public int getAlluserNum() {
		return alluserNum;
	}
	public void setAlluserNum(int alluserNum) {
		this.alluserNum = alluserNum;
	}


}
