package com.zzy.brd.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicUpdate;

import com.zzy.brd.entity.interfaces.IdEntity;

/**
 * @author:xpk
 *    2016年9月22日-下午6:00:59
 **/
@Entity
@Table(name = "flow_withdraw")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate(value = true)
public class FlowWithdraw extends IdEntity {
	
	public enum WithdrawStatus {
		/**待审核*/
		NOCHECK,
		/**未放款*/
		NOLENDING, 
		/** 体现失败*/
		FAILCHECK,
		/**已提现*/
		ALEARDYLOAN,
		;
		public String getDes() {
			if(this == NOCHECK)
				return "待审核";
			if(this==NOLENDING)
				return "待放款";
			if(this == FAILCHECK)
				return "提现失败";
			if(this == ALEARDYLOAN)
				return "已提现";
			return "";					
		}		
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = -6350027279451244057L;
	/**提现流水编号*/
	private String flowno;
	
	/**关联的用户*/
	@ManyToOne(targetEntity=User.class,fetch=FetchType.LAZY)
	@JoinColumn(name="user_id",referencedColumnName="id")
	private User user;
	
	/**提现的金额*/
	private BigDecimal money;
	
	private Date createTime;
	
	@Column(name="status")
	private WithdrawStatus status;
	
	/**审核时间*/
	private Date verifyTime;
	
	/**审核人*/
	@ManyToOne(targetEntity=User.class,fetch=FetchType.LAZY)
	@JoinColumn(name="verify_users",referencedColumnName="id")
	private User verifyUser;
	
	/**拒绝原因*/
	private String rejectReason;
	
	/**开户银行*/
	private String bankname;
	/**开户名*/
	private String accountname;
	/**银行的帐号*/
	@Column(name="account")
	private String bankaccount;
	/**开户的地区 省*/
	private String province;
	/**开户的城市*/
	private String city;
	/**开户的地区*/
	private String area;
	/**手机号码*/
	private String telephone;
	/**发放时间*/
	private Date sendDate;
	@OneToMany(targetEntity=OrderOperLog.class,mappedBy="withdraw",fetch=FetchType.LAZY)
	@OrderBy("create_time asc")
	private List<OrderOperLog> operLogs = new ArrayList<>();

	public String getFlowno() {
		return flowno;
	}

	public void setFlowno(String flowno) {
		this.flowno = flowno;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public WithdrawStatus getStatus() {
		return status;
	}

	public void setStatus(WithdrawStatus status) {
		this.status = status;
	}

	public Date getVerifyTime() {
		return verifyTime;
	}

	public void setVerifyTime(Date verifyTime) {
		this.verifyTime = verifyTime;
	}

	public User getVerifyUser() {
		return verifyUser;
	}

	public void setVerifyUser(User verifyUser) {
		this.verifyUser = verifyUser;
	}

	public String getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	public String getBankname() {
		return bankname;
	}

	public void setBankname(String bankname) {
		this.bankname = bankname;
	}

	public String getAccountname() {
		return accountname;
	}

	public void setAccountname(String accountname) {
		this.accountname = accountname;
	}

	
	public String getBankaccount() {
		return bankaccount;
	}

	public void setBankaccount(String bankaccount) {
		this.bankaccount = bankaccount;
	}

	public List<OrderOperLog> getOperLogs() {
		return operLogs;
	}

	public void setOperLogs(List<OrderOperLog> operLogs) {
		this.operLogs = operLogs;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}	
}
