package com.zzy.brd.mobile.web.dto.req.bankinfo;

import javax.validation.constraints.NotNull;


/**
 * @author:xpk
 *    2016年9月30日-下午6:14:52
 **/
public class ReqAddUserBankinfoDTO {
	
	private String name;
	
	private String bankname;
	
	private String bankaccount;
	
	private String province;
	
	private String city;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBankname() {
		return bankname;
	}

	public void setBankname(String bankname) {
		this.bankname = bankname;
	}

	public String getBankaccount() {
		return bankaccount;
	}

	public void setBankaccount(String bankaccount) {
		this.bankaccount = bankaccount;
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

}
