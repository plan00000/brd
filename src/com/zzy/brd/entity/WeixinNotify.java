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
 *    2016年9月27日-上午9:00:02
 **/
@Entity
@Table(name = "weixin_notify")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate(value = true)
public class WeixinNotify extends IdEntity {

	public enum NotifyType {
		
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7947858448760077007L;
	
	private NotifyType notifyType;
	
	@ManyToOne(targetEntity=User.class,fetch=FetchType.LAZY)
	@JoinColumn(name="user_id",referencedColumnName="id")
	private User user;
	
	@ManyToOne(targetEntity=Orderform.class,fetch=FetchType.LAZY)
	@JoinColumn(name="order_id",referencedColumnName="id")
	private Orderform orderform;
	
	@ManyToOne(targetEntity=FlowWithdraw.class,fetch=FetchType.LAZY)
	@JoinColumn(name="withdraw_id",referencedColumnName="id")
	private FlowWithdraw withdraw;
	
	private Date createTime;
	
	private String remark;
	
	private String template;
	
	private String first;
	
	private String keynote1;
	
	private String keynote2;
	
	private String keynote3;

	public NotifyType getNotifyType() {
		return notifyType;
	}

	public void setNotifyType(NotifyType notifyType) {
		this.notifyType = notifyType;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Orderform getOrderform() {
		return orderform;
	}

	public void setOrderform(Orderform orderform) {
		this.orderform = orderform;
	}

	public FlowWithdraw getWithdraw() {
		return withdraw;
	}

	public void setWithdraw(FlowWithdraw withdraw) {
		this.withdraw = withdraw;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getFirst() {
		return first;
	}

	public void setFirst(String first) {
		this.first = first;
	}

	public String getKeynote1() {
		return keynote1;
	}

	public void setKeynote1(String keynote1) {
		this.keynote1 = keynote1;
	}

	public String getKeynote2() {
		return keynote2;
	}

	public void setKeynote2(String keynote2) {
		this.keynote2 = keynote2;
	}

	public String getKeynote3() {
		return keynote3;
	}

	public void setKeynote3(String keynote3) {
		this.keynote3 = keynote3;
	}
}
