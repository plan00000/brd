package com.zzy.brd.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicUpdate;

import com.zzy.brd.entity.interfaces.IdEntity;

/**
 * @author:xpk
 *    2016年9月23日-上午9:08:40
 **/
@Entity
@Table(name = "order_oper_log")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate(value = true)
public class OrderOperLog extends IdEntity  {
	
	public enum OrderformOperatType {
		/***其他类型的**/
		OTHEROPERA,
		/**待审核*/
		UNCHECKED,
		/**待放款*/
		UNLOAN,
		/**已放款*/
		LOANED,
		/**审核失败或者为无效*/
		CHECKFAIL,
		;	
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8650129413519560509L;
	
	/**操作人*/
	@ManyToOne(targetEntity=User.class,fetch=FetchType.LAZY)
	@JoinColumn(name="opertor_id",referencedColumnName="id")
	private User opertor;
	
	/**操作的内容*/
	private String operContent;
	
	/**创建时间*/
	private Date createTime;
	
	/**对应的订单*/
	@ManyToOne(targetEntity=Orderform.class,fetch=FetchType.LAZY)
	@JoinColumn(name="orderform_id",referencedColumnName="id")
	private Orderform order;
	
	/**对应的提现订单*/
	@ManyToOne(targetEntity=FlowWithdraw.class,fetch=FetchType.LAZY)
	@JoinColumn(name="withdraw_id",referencedColumnName="id")
	private FlowWithdraw withdraw;
	
	@OneToOne(cascade=CascadeType.ALL,targetEntity=OrderformProductRecord.class,fetch=FetchType.LAZY)
	@JoinColumn(name="product_change_record_id",referencedColumnName="id")
	private OrderformProductRecord productChangeRecord;
	
	private OrderformOperatType operatType=OrderformOperatType.OTHEROPERA;	
	
	public User getOpertor() {
		return opertor;
	}

	public void setOpertor(User opertor) {
		this.opertor = opertor;
	}

	public String getOperContent() {
		return operContent;
	}

	public void setOperContent(String operContent) {
		this.operContent = operContent;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Orderform getOrder() {
		return order;
	}

	public void setOrder(Orderform order) {
		this.order = order;
	}

	public FlowWithdraw getWithdraw() {
		return withdraw;
	}

	public void setWithdraw(FlowWithdraw withdraw) {
		this.withdraw = withdraw;
	}

	public OrderformProductRecord getProductChangeRecord() {
		return productChangeRecord;
	}

	public void setProductChangeRecord(OrderformProductRecord productChangeRecord) {
		this.productChangeRecord = productChangeRecord;
	}

	public OrderformOperatType getOperatType() {
		return operatType;
	}

	public void setOperatType(OrderformOperatType operatType) {
		this.operatType = operatType;
	}	
		
}
