package com.zzy.brd.dto.rep.admin.user;

import java.util.Date;

import com.zzy.brd.entity.User;

/**
 * @author:xpk
 *    2016年11月1日-上午8:57:40
 **/
public class RepApprenticesRecordDTO {

	public Date registerTime; 
	
	public String account ;
	
	public String name ; 
	
	public int orderSum;
	
	public int sonsSum;
	
	public RepApprenticesRecordDTO(User user){
		this.registerTime = user.getCreatedate();
		this.account = user.getMobileno();
		if(user.getRealname()==null){
			this.name = user.getUsername();
		}else{
			this.name = user.getRealname();
		}

	}
	
	public Date getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getOrderSum() {
		return orderSum;
	}

	public void setOrderSum(int orderSum) {
		this.orderSum = orderSum;
	}

	public int getSonsSum() {
		return sonsSum;
	}

	public void setSonsSum(int sonsSum) {
		this.sonsSum = sonsSum;
	}
}
