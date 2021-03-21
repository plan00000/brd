package com.zzy.brd.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicUpdate;

import com.zzy.brd.entity.interfaces.IdEntity;

/**
 * @author:xpk
 *    2016年9月22日-下午5:59:10
 **/
@Entity
@Table(name = "orderform")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate(value = true)
public class Orderform extends IdEntity {

	public enum OrderSource {
		PC,
		WECHAT,
		;
	}
	
	public enum OrderformStatus {
		/**待联系*/
		UNCONTACTED, 
		/**待面谈*/
		UNTOLKWITH, 
		/**待审核*/
		UNCHECKED,
		/**待放款*/
		UNLOAN,
		/**审核失败*/
		CHECKFAIL,
		/**已放款*/
		LOANED,
		/**无效*/
		INVALID,
		;
		public String getDes(){
			if(this ==UNCONTACTED)
				return "待联系";
			if(this==UNTOLKWITH)
				return "待面谈";
			if(this==UNCHECKED)
				return "待审核";
			if(this== UNLOAN)
				return "待放款";
			if(this== CHECKFAIL)
				return "审核失败";
			if(this==LOANED)
				return "已放款";
			if(this==INVALID)
				return "无效订单";
			return "";
		}
		
	}
	
	public enum PaymentBrokerageType {
		DISPOSABLE, /**一次性发放*/
		MANYTIMES, /**多次发放*/
		;
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2546183983898007274L;
	
	@ManyToOne(targetEntity=User.class,fetch=FetchType.LAZY)
	@JoinColumn(name="user_id",referencedColumnName="id")
	private User user;

	private OrderSource source;
	/** 姓名*/
	private String name ="";
	/** 身份证后6位*/
	private String idcard = "";
	/** 手机号码*/
	private String telephone = "";
	/** 所在地省份*/
	private String province ="福建省";
	/** 城市*/
	private String city = "厦门市";
	
	@ManyToOne(targetEntity=Product.class,fetch=FetchType.LAZY)
	@JoinColumn(name="product_id",referencedColumnName="id")
	private Product product;
	
	@ManyToOne(targetEntity=Product.class,fetch=FetchType.LAZY)
	@JoinColumn(name="old_product_id",referencedColumnName="id")
	private Product oldProduct;
	
	@ManyToOne(targetEntity=ProductInfo.class,fetch=FetchType.LAZY)
	@JoinColumn(name="product_info_id",referencedColumnName="id")
	private ProductInfo productInfo;
	
	@ManyToOne(targetEntity=User.class,fetch=FetchType.LAZY)
	@JoinColumn(name="old_parent_id",referencedColumnName="id")
	private User oldParent;
	
	@ManyToOne(targetEntity=User.class,fetch=FetchType.LAZY)
	@JoinColumn(name="old_bussiness_id",referencedColumnName="id")
	private User oldBussiness;
	
	@ManyToOne(targetEntity=User.class,fetch=FetchType.LAZY)
	@JoinColumn(name="old_salesman_id",referencedColumnName="id")
	private User oldSalesman;
	
	/** 贷款金额*/
	private BigDecimal money ;
	/** 实际贷款金额*/
	private BigDecimal actualMoney;
	/** 贷款期限*/
	private int loanTime;
	/** 实际贷款期限*/
	private int oldLoanTime;
	/** 实际下单人佣金*/
	private BigDecimal brokerageRateNum;
	/** 贷款利率*/
	private BigDecimal loanInsterestRate;
	/** 备注*/
	private String remark;
	/** 订单状态*/
	private OrderformStatus status;
	/** 订单编号*/
	private String orderNo;
	/** 合同编号*/
	private String comtractNum;
	/** 订单无效原因*/
	private String invalidReason;
	/** 佣金发放类型*/
	private PaymentBrokerageType paymentBrokerageType;
	/** 用户加价息差值*/
	private BigDecimal spreadRate;
	/** 用户提成比例*/
	private BigDecimal percentageRate;
	
	/**预计佣金总和 */
	private BigDecimal brokerageRateTotal;
	/** 实际贷款利率*/
	private BigDecimal actualLoanInsterestRate;

	/** 下单人预计佣金*/
	private BigDecimal selfBrokerageNum;
	
	@OneToMany(targetEntity=OrderOperLog.class,mappedBy="order",fetch=FetchType.LAZY)
	@OrderBy("create_time asc")
	private List<OrderOperLog> operLogs = new ArrayList<>();
	/** 创建时间*/
	private Date createTime;
	
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public OrderSource getSource() {
		return source;
	}

	public void setSource(OrderSource source) {
		this.source = source;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
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

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public BigDecimal getActualMoney() {
		return actualMoney;
	}

	public void setActualMoney(BigDecimal actualMoney) {
		this.actualMoney = actualMoney;
	}

	public int getLoanTime() {
		return loanTime;
	}

	public void setLoanTime(int loanTime) {
		this.loanTime = loanTime;
	}

	public BigDecimal getBrokerageRateNum() {
		return brokerageRateNum;
	}

	public void setBrokerageRateNum(BigDecimal brokerageRateNum) {
		this.brokerageRateNum = brokerageRateNum;
	}


	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public OrderformStatus getStatus() {
		return status;
	}

	public void setStatus(OrderformStatus status) {
		this.status = status;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public BigDecimal getLoanInsterestRate() {
		return loanInsterestRate;
	}

	public void setLoanInsterestRate(BigDecimal loanInsterestRate) {
		this.loanInsterestRate = loanInsterestRate;
	}

	public String getComtractNum() {
		return comtractNum;
	}

	public void setComtractNum(String comtractNum) {
		this.comtractNum = comtractNum;
	}

	public String getInvalidReason() {
		return invalidReason;
	}

	public void setInvalidReason(String invalidReason) {
		this.invalidReason = invalidReason;
	}

	public PaymentBrokerageType getPaymentBrokerageType() {
		return paymentBrokerageType;
	}

	public void setPaymentBrokerageType(PaymentBrokerageType paymentBrokerageType) {
		this.paymentBrokerageType = paymentBrokerageType;
	}

	public List<OrderOperLog> getOperLogs() {
		return operLogs;
	}

	public void setOperLogs(List<OrderOperLog> operLogs) {
		this.operLogs = operLogs;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


	public BigDecimal getSpreadRate() {
		return spreadRate;
	}


	public void setSpreadRate(BigDecimal spreadRate) {
		this.spreadRate = spreadRate;
	}

	public BigDecimal getPercentageRate() {
		return percentageRate;
	}

	public void setPercentageRate(BigDecimal percentageRate) {
		this.percentageRate = percentageRate;
	}

	public BigDecimal getBrokerageRateTotal() {
		return brokerageRateTotal;
	}

	public void setBrokerageRateTotal(BigDecimal brokerageRateTotal) {
		this.brokerageRateTotal = brokerageRateTotal;
	}

	public BigDecimal getActualLoanInsterestRate() {
		return actualLoanInsterestRate;
	}

	public void setActualLoanInsterestRate(BigDecimal actualLoanInsterestRate) {
		this.actualLoanInsterestRate = actualLoanInsterestRate;
	}

	public BigDecimal getSelfBrokerageNum() {
		return selfBrokerageNum;
	}

	public void setSelfBrokerageNum(BigDecimal selfBrokerageNum) {
		this.selfBrokerageNum = selfBrokerageNum;
	}

	public Product getOldProduct() {
		return oldProduct;
	}

	public void setOldProduct(Product oldProduct) {
		this.oldProduct = oldProduct;
	}

	public User getOldParent() {
		return oldParent;
	}

	public void setOldParent(User oldParent) {
		this.oldParent = oldParent;
	}

	public User getOldBussiness() {
		return oldBussiness;
	}

	public void setOldBussiness(User oldBussiness) {
		this.oldBussiness = oldBussiness;
	}

	public User getOldSalesman() {
		return oldSalesman;
	}

	public void setOldSalesman(User oldSalesman) {
		this.oldSalesman = oldSalesman;
	}

	public int getOldLoanTime() {
		return oldLoanTime;
	}

	public void setOldLoanTime(int oldLoanTime) {
		this.oldLoanTime = oldLoanTime;
	}
	
}
