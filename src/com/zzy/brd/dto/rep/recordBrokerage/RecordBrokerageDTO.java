package com.zzy.brd.dto.rep.recordBrokerage;

import java.math.BigDecimal;

import com.zzy.brd.entity.RecordBrokerage;
import com.zzy.brd.util.date.DateUtil;

/***
 * 用于佣金详情页列表
 * 
 * @author 
 *
 */
public class RecordBrokerageDTO {
	private String orderNo;//订单编号
	private Long orderId;//订单ID
	private String sendBrokerage;//佣金发放时间
	private String productName;//产品名称
	private BigDecimal money ;//贷款的金额
	private BigDecimal haveBrokerage ;//获得佣金
	private String name ;//下单人名称
	private String relate ;//下单人名称
	
	public RecordBrokerageDTO(RecordBrokerage recordBrokerage) {
		this.orderNo = recordBrokerage.getOrder().getOrderNo();
		this.orderId = recordBrokerage.getOrder().getId();
		this.sendBrokerage = DateUtil.DateToString(recordBrokerage.getSendBrokerage());
		this.productName = recordBrokerage.getOrder().getProductInfo().getProductName();
		this.money = recordBrokerage.getOrder().getMoney();
		this.haveBrokerage = recordBrokerage.getHaveBrokerage();
		this.name = recordBrokerage.getOrder().getUser().getUsername();
		this.relate = recordBrokerage.getRelate().getDes();
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getSendBrokerage() {
		return sendBrokerage;
	}

	public void setSendBrokerage(String sendBrokerage) {
		this.sendBrokerage = sendBrokerage;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public BigDecimal getHaveBrokerage() {
		return haveBrokerage;
	}

	public void setHaveBrokerage(BigDecimal haveBrokerage) {
		this.haveBrokerage = haveBrokerage;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRelate() {
		return relate;
	}

	public void setRelate(String relate) {
		this.relate = relate;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

}
