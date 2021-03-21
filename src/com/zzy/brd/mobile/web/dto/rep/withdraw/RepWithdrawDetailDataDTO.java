package com.zzy.brd.mobile.web.dto.rep.withdraw;

import java.math.BigDecimal;
import java.util.Date;

import com.zzy.brd.entity.FlowWithdraw;
import com.zzy.brd.entity.FlowWithdraw.WithdrawStatus;
import com.zzy.brd.util.tld.DateUtils;
import com.zzy.brd.util.tld.PriceUtils;

/**
 * @author:xpk
 *    2016年9月30日-下午3:24:05
 **/
public class RepWithdrawDetailDataDTO {
	
	private String statusStr;
	
	private String createTime;
	
	private String money;
	
	private String remark;
	
	private String sendTime = new Date().toString();
	
	public RepWithdrawDetailDataDTO() {
		
	}
	
	public RepWithdrawDetailDataDTO(FlowWithdraw withdraw) {
		this.statusStr = withdraw.getStatus().getDes();
		if(statusStr.equals(WithdrawStatus.NOLENDING.getDes())){
			this.statusStr = "提现中";
		}
		this.createTime =DateUtils.formatNormalDate(withdraw.getCreateTime());
		this.money = PriceUtils.normalPrice(withdraw.getMoney());
		if(withdraw.getStatus().equals(WithdrawStatus.FAILCHECK)){
			this.remark = withdraw.getRejectReason();
		}		
		if(withdraw.getStatus().equals(WithdrawStatus.ALEARDYLOAN)){
			this.sendTime = DateUtils.formatNormalDate(withdraw.getSendDate());
		}
	}
	
	
	public String getStatusStr() {
		return statusStr;
	}

	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}
	
	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
}
