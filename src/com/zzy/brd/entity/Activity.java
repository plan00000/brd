package com.zzy.brd.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicUpdate;

import com.zzy.brd.entity.interfaces.IdEntity;

/**
 * @author:xpk
 *    2016年9月22日-上午11:12:38
 **/
@Entity
@Table(name = "activity")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate(value = true)
public class Activity extends IdEntity {
	
	public enum ActivitySet {
		OFF, /**关闭活动*/
		REGISTER, /**注册送红包*/
		REGISTERCODE, /**推荐注册活动*/
		;
		public String getDes(){
			if(this == OFF)
				return "活动关闭";
			if(this == REGISTER )
				return "注册送红包";
			if(this == REGISTERCODE)
				return "推荐注册送红包";
			return "";
		}	
	}
	
	public enum ActivityType {
		STARTORDER, /**星级订单*/
		BONUSAWARD, /**收徒奖励*/
		RECOMMEND,  /**推荐注册活动*/
		; 		
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3941996013963902068L;
	
	private String picurl;
	/**活动规则*/
	@Column(name="activity_rule")
	private String activityRule;
	/**最大收徒数或者订单数*/
	@Column(name="max_num")
	private int maxNum;
	
	/**星级订单不计时间订单数*/
	@Column(name="star_ordernum")
	private int starOrdernum;
	
	/**收徒奖励金额*/	
	@Column(name="bonus_amount")
	private BigDecimal bonusAmount;
	
	/**活动设置*/
	@Column(name="activity_set")
	private ActivitySet activitySet;
	
	/**活动类型 */
	@Column(name="activity_type")
	private ActivityType activityType;
	/**活动文案 */
	@Column(name="activity_copy")
	private String activityCopy;
	/**活动对象 */
	@Column(name="activity_object")
	private String activityObject;
	
	public String getPicurl() {
		return picurl;
	}

	public void setPicurl(String picurl) {
		this.picurl = picurl;
	}

	public String getActivityRule() {
		return activityRule;
	}

	public int getStarOrdernum() {
		return starOrdernum;
	}

	public void setStarOrdernum(int starOrdernum) {
		this.starOrdernum = starOrdernum;
	}

	public void setActivityRule(String activityRule) {
		this.activityRule = activityRule;
	}

	public int getMaxNum() {
		return maxNum;
	}

	public void setMaxNum(int maxNum) {
		this.maxNum = maxNum;
	}

	public BigDecimal getBonusAmount() {
		return bonusAmount;
	}

	public void setBonusAmount(BigDecimal bonusAmount) {
		this.bonusAmount = bonusAmount;
	}

	public ActivitySet getActivitySet() {
		return activitySet;
	}

	public void setActivitySet(ActivitySet activitySet) {
		this.activitySet = activitySet;
	}

	public ActivityType getActivityType() {
		return activityType;
	}

	public void setActivityType(ActivityType activityType) {
		this.activityType = activityType;
	}

	public String getActivityCopy() {
		return activityCopy;
	}

	public void setActivityCopy(String activityCopy) {
		this.activityCopy = activityCopy;
	}

	public String getActivityObject() {
		return activityObject;
	}

	public void setActivityObject(String activityObject) {
		this.activityObject = activityObject;
	}
	
	
}
