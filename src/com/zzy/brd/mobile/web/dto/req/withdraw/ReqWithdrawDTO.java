package com.zzy.brd.mobile.web.dto.req.withdraw;

import java.math.BigDecimal;

/**
 * @author:xpk
 *    2016年9月30日-下午4:17:18
 **/
public class ReqWithdrawDTO {
	
	private long bankinfoId;
	
	private BigDecimal money;
	
	private String code;
	
	private String password;

	public long getBankinfoId() {
		return bankinfoId;
	}

	public void setBankinfoId(long bankinfoId) {
		this.bankinfoId = bankinfoId;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
