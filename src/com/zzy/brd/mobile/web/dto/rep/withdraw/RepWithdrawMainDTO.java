package com.zzy.brd.mobile.web.dto.rep.withdraw;

import java.math.BigDecimal;
import java.util.Date;

import org.springside.modules.mapper.BeanMapper;

import com.zzy.brd.entity.FlowWithdraw.WithdrawStatus;
import com.zzy.brd.entity.User;
import com.zzy.brd.entity.UserBankinfo;

/**
 * @author:xpk
 *    2016年11月22日-下午5:51:28
 **/
public class RepWithdrawMainDTO {

	private long id;
	/**提现流水编号*/
	private String flowno;
	
	/**关联的用户*/
	private User user;
	
	/**提现的金额*/
	private BigDecimal money;
	
	private Date createTime;
	
	private WithdrawStatus status;
	
	/**审核时间*/
	private Date verifyTime;
	
	/**审核人*/
	private User verifyUser;
	
	/**拒绝原因*/
	private String rejectReason;
	
	/**开户银行*/
	private String bankname;
	/**开户名*/
	private String accountname;
	/**银行的帐号*/
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
	
	public RepWithdrawMainDTO(UserBankinfo info){
		BeanMapper.copy(info,this);
		if(info.getBankaccount().length()>4){
			this.bankaccount = info.getBankaccount().substring(info.getBankaccount().length()-4);		
		}else{
			this.bankaccount = info.getBankaccount();
		}
	}
	
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
		if(bankaccount.length()>4){
			this.bankaccount = bankaccount.substring(bankaccount.length()-4,bankaccount.length());
		}else{
			this.bankaccount = bankaccount;
		}
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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}	

}
