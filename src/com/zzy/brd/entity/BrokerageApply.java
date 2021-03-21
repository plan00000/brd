package com.zzy.brd.entity;

import java.math.BigDecimal;
import java.util.Date;

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
 *    2016年9月26日-下午5:24:46
 **/
@Entity
@Table(name = "brokerage_apply")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate(value = true)
public class BrokerageApply extends IdEntity {
	
	public enum BrokerageApplyStatus {
		/** 待录佣金*/
		UNENTERING,
		/** 风控审核*/
		RISKCHECK,
		/** ceo审核*/
		CEOCHECK,
		/** 财务发放*/
		FINANCESEND,
		/** 佣金多次发放*/
		MANYTIMES,
		/** ceo确定*/
		CEOPASS,
		/** 已发佣金*/
		HADSEND,
		/** 失败订单*/
		FAILORDER
		;
		public String getDes(){
			if(this == UNENTERING)
				return "待录佣金";
			if(this == RISKCHECK)
				return "风控审核";
			if(this == CEOCHECK)
				return "CEO审核";
			if(this == FINANCESEND)
				return "财务发放";
			if(this == MANYTIMES)
				return "佣金多次发放";
			if(this == CEOPASS)
				return "CEO确定";
			if(this == HADSEND)
				return "已发佣金";
			if(this == FAILORDER)
				return "失败订单";
			return "";
		}
	}
	public enum SendStatus{
		/** 单次*/
		SINGAL,
		/** 多次*/
		MANEY;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 6371162726761380159L;
	
	/** 佣金订单编号*/
	private String brokerageNo;
	
	/** 佣金订单创建时间*/
	private Date createTime;
	
	/**体担任佣金*/
	private BigDecimal selfBrokerage;
	
	/**师傅佣金*/
	private BigDecimal fatherBrokerage;
	
	/**业务员佣金*/	
	private BigDecimal salesmanBrokerage;
	
	/**商家佣金*/
	private BigDecimal businessBrokerage;
	
	/**贷款金额*/
	private BigDecimal money;
	
	/**贷款利率*/
	private BigDecimal interest;
	
	/**状态*/
	private BrokerageApplyStatus status;
	
	/**拒绝原因*/
	private String rejectReason;
	
	/**剩余发放佣金*/
//	private BigDecimal leftBrokerage;
//	
//	/**单词发放佣金*/
//	private BigDecimal singleBrokerage;
	private SendStatus sendStatus;
	/** 发放次数*/
	private int sendTimes;
	
	/** 已发放次数*/
	private int hasSendTimes;
	
	/** 提单人剩余佣金*/
	private BigDecimal selfResidualBrokerage;
	/** 师父剩余佣金*/
	private BigDecimal fatherResidualBrokerage;
	/** 商家剩余佣金*/
	private BigDecimal businessResidualBrokerage;
	/** 业务员剩余佣金*/
	private BigDecimal salesmanResidualBrokerage;
	
	private BigDecimal selfSubmitBrokerage;
	private BigDecimal fatherSubmitBrokerage;
	private BigDecimal businessSubmitBrokerage;
	private BigDecimal salesmanSubmitBrokerage;
	
	//佣金审核通过时间
	private Date checkpassTime;
	
	@ManyToOne(targetEntity=User.class,fetch=FetchType.LAZY)
	@JoinColumn(name="user_id",referencedColumnName="id")
	private User user;
	
	@ManyToOne(targetEntity = Product.class,fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id",referencedColumnName = "id")
	private Product product;
	
	@ManyToOne(targetEntity = ProductInfo.class,fetch = FetchType.LAZY)
	@JoinColumn(name = "product_info_id",referencedColumnName = "id")
	private ProductInfo productInfo;
	
	@OneToOne(targetEntity = Orderform.class,fetch = FetchType.LAZY)
	@JoinColumn(name = "orderform_id",referencedColumnName = "id")
	private Orderform orderform;
	
	public String getBrokerageNo() {
		return brokerageNo;
	}

	public void setBrokerageNo(String brokerageNo) {
		this.brokerageNo = brokerageNo;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public BigDecimal getSelfBrokerage() {
		return selfBrokerage;
	}

	public void setSelfBrokerage(BigDecimal selfBrokerage) {
		this.selfBrokerage = selfBrokerage;
	}

	public BigDecimal getFatherBrokerage() {
		return fatherBrokerage;
	}

	public void setFatherBrokerage(BigDecimal fatherBrokerage) {
		this.fatherBrokerage = fatherBrokerage;
	}

	public BigDecimal getSalesmanBrokerage() {
		return salesmanBrokerage;
	}

	public void setSalesmanBrokerage(BigDecimal salesmanBrokerage) {
		this.salesmanBrokerage = salesmanBrokerage;
	}

	public BigDecimal getBusinessBrokerage() {
		return businessBrokerage;
	}

	public void setBusinessBrokerage(BigDecimal businessBrokerage) {
		this.businessBrokerage = businessBrokerage;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public BigDecimal getInterest() {
		return interest;
	}

	public void setInterest(BigDecimal interest) {
		this.interest = interest;
	}

	public BrokerageApplyStatus getStatus() {
		return status;
	}

	public void setStatus(BrokerageApplyStatus status) {
		this.status = status;
	}

	public String getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}


	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public ProductInfo getProductInfo() {
		return productInfo;
	}

	public void setProductInfo(ProductInfo productInfo) {
		this.productInfo = productInfo;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Orderform getOrderform() {
		return orderform;
	}

	public void setOrderform(Orderform orderform) {
		this.orderform = orderform;
	}

	public SendStatus getSendStatus() {
		return sendStatus;
	}

	public void setSendStatus(SendStatus sendStatus) {
		this.sendStatus = sendStatus;
	}

	public int getSendTimes() {
		return sendTimes;
	}

	public void setSendTimes(int sendTimes) {
		this.sendTimes = sendTimes;
	}

	public BigDecimal getSelfResidualBrokerage() {
		return selfResidualBrokerage;
	}

	public int getHasSendTimes() {
		return hasSendTimes;
	}

	public void setHasSendTimes(int hasSendTimes) {
		this.hasSendTimes = hasSendTimes;
	}

	public void setSelfResidualBrokerage(BigDecimal selfResidualBrokerage) {
		this.selfResidualBrokerage = selfResidualBrokerage;
	}

	public BigDecimal getFatherResidualBrokerage() {
		return fatherResidualBrokerage;
	}

	public void setFatherResidualBrokerage(BigDecimal fatherResidualBrokerage) {
		this.fatherResidualBrokerage = fatherResidualBrokerage;
	}

	public BigDecimal getBusinessResidualBrokerage() {
		return businessResidualBrokerage;
	}

	public void setBusinessResidualBrokerage(BigDecimal businessResidualBrokerage) {
		this.businessResidualBrokerage = businessResidualBrokerage;
	}

	public BigDecimal getSalesmanResidualBrokerage() {
		return salesmanResidualBrokerage;
	}

	public void setSalesmanResidualBrokerage(BigDecimal salesmanResidualBrokerage) {
		this.salesmanResidualBrokerage = salesmanResidualBrokerage;
	}

	public BigDecimal getSelfSubmitBrokerage() {
		return selfSubmitBrokerage;
	}

	public void setSelfSubmitBrokerage(BigDecimal selfSubmitBrokerage) {
		this.selfSubmitBrokerage = selfSubmitBrokerage;
	}

	public BigDecimal getFatherSubmitBrokerage() {
		return fatherSubmitBrokerage;
	}

	public void setFatherSubmitBrokerage(BigDecimal fatherSubmitBrokerage) {
		this.fatherSubmitBrokerage = fatherSubmitBrokerage;
	}

	public BigDecimal getBusinessSubmitBrokerage() {
		return businessSubmitBrokerage;
	}

	public void setBusinessSubmitBrokerage(BigDecimal businessSubmitBrokerage) {
		this.businessSubmitBrokerage = businessSubmitBrokerage;
	}

	public BigDecimal getSalesmanSubmitBrokerage() {
		return salesmanSubmitBrokerage;
	}

	public void setSalesmanSubmitBrokerage(BigDecimal salesmanSubmitBrokerage) {
		this.salesmanSubmitBrokerage = salesmanSubmitBrokerage;
	}

	public Date getCheckpassTime() {
		return checkpassTime;
	}

	public void setCheckpassTime(Date checkpassTime) {
		this.checkpassTime = checkpassTime;
	}

}
