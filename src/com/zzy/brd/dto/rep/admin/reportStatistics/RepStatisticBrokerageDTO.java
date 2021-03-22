package com.zzy.brd.dto.rep.admin.reportStatistics;

import java.math.BigDecimal;

import com.zzy.brd.entity.BrokerageApply;
import com.zzy.brd.entity.User;

/***
 * 佣金统计
 * @author wwy
 *
 */
public class RepStatisticBrokerageDTO {

	/** 累计佣金 */
	private BigDecimal addBrokerage = BigDecimal.ZERO;
	/** 用户名 */
	private String username; 
	/** 用户ID */
	private Long userId; 
	/** 部门名称 */
	private String departname;
	/** 会员身份 */
	private String userType;
	/** 订单编号*/
	private String orderNo;
	/** 订单ID*/
	private Long orderId;
	
	public RepStatisticBrokerageDTO(User user,BigDecimal addBrokerage) {
		this.username = user.getUsername();
		this.userId = user.getId();
		this.addBrokerage = addBrokerage;
		User.UserType usertype = user.getUserType();
		this.userType = usertype.getStr();
		/*if(usertype==User.UserType.SALESMAN){
			this.departname = user.getUserInfoEmployee().getDepartment().getName();
		}*/
	}
	public RepStatisticBrokerageDTO(BrokerageApply brokerageApply,BigDecimal addBrokerage) {
		this.addBrokerage = addBrokerage;
		this.username = brokerageApply.getOrderform().getUser().getUsername();
		this.userId = brokerageApply.getOrderform().getUser().getId();
		this.orderNo = brokerageApply.getBrokerageNo();
		this.orderId = brokerageApply.getId();
	}
	
	public BigDecimal getAddBrokerage() {
		return addBrokerage;
	}
	public void setAddBrokerage(BigDecimal addBrokerage) {
		this.addBrokerage = addBrokerage;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getDepartname() {
		return departname;
	}
	public void setDepartname(String departname) {
		this.departname = departname;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	
}
