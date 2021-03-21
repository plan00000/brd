package com.zzy.brd.dto.rep.admin.reportStatistics;

import java.math.BigDecimal;

import com.zzy.brd.entity.ActivityStarOrder;
import com.zzy.brd.entity.User;
import com.zzy.brd.entity.UserInfoBoth.StarOrderAwardFlag;

/**
 * 统计的订单数百分比
 * 
 * @author csy 2016年10月13日
 */
public class RepOrderStatisticsDTO {

	/** 普通会员订单数百分比 */
	private String usercountPercent;
	/** 融资经理订单数百分比  */
	private String managercountPercent;
	/** 商家订单数百分比  */
	private String sellercountPercent;
	/** 公司内部订单数百分比  */
	private String othercountPercent;
	/** 普通会员订单数 */
	private int usercount;
	/** 融资经理订单数  */
	private int managercount;
	/** 商家订单数  */
	private int sellercount;
	/** 公司内部订单数  */
	private int othercount;
	public String getUsercountPercent() {
		return usercountPercent;
	}
	public void setUsercountPercent(String usercountPercent) {
		this.usercountPercent = usercountPercent;
	}
	public String getManagercountPercent() {
		return managercountPercent;
	}
	public void setManagercountPercent(String managercountPercent) {
		this.managercountPercent = managercountPercent;
	}
	public String getSellercountPercent() {
		return sellercountPercent;
	}
	public void setSellercountPercent(String sellercountPercent) {
		this.sellercountPercent = sellercountPercent;
	}
	public String getOthercountPercent() {
		return othercountPercent;
	}
	public void setOthercountPercent(String othercountPercent) {
		this.othercountPercent = othercountPercent;
	}
	public int getUsercount() {
		return usercount;
	}
	public void setUsercount(int usercount) {
		this.usercount = usercount;
	}
	public int getManagercount() {
		return managercount;
	}
	public void setManagercount(int managercount) {
		this.managercount = managercount;
	}
	public int getSellercount() {
		return sellercount;
	}
	public void setSellercount(int sellercount) {
		this.sellercount = sellercount;
	}
	public int getOthercount() {
		return othercount;
	}
	public void setOthercount(int othercount) {
		this.othercount = othercount;
	}
	

}
